package com.study.studyproject.global.jwt;

import com.study.studyproject.entity.RefreshToken;
import com.study.studyproject.global.auth.UserDetailsServiceImpl;
import com.study.studyproject.global.exception.ex.TokenNotValidatException;
import com.study.studyproject.login.dto.TokenDtoResponse;
import com.study.studyproject.login.repository.RefreshRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshRepository refreshTokenRepository;

    private static final long ACCESS_TIME = 60 * 40 * 1000L;
    private static final long REFRESH_TIME =  60 *60* 1000L;
    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";
    public static final String BEARER = "Bearer ";




    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // bean으로 등록 되면서 딱 한번 실행이 됩니다.
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오는 기능
    public String getHeaderToken(HttpServletRequest request, String type) {
        return type.equals(ACCESS_TOKEN) ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);
    }

    // 토큰 생성
    public TokenDtoResponse createAllToken(String email, Long id) {
        String access = creatAccessToken(email);
        System.out.println("발급access = " + access);
        String refresh = createRefreshToken(email, id);
        System.out.println("발급 refresh = " + refresh);

        return new TokenDtoResponse(access,refresh
        );
    }

    public String creatAccessToken(String email) {
        Date date = new Date();
        long time =  ACCESS_TIME;

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(date.getTime() + time))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();

    }

    public String createRefreshToken(String email,Long id) {
        Date date = new Date();
        long time =  REFRESH_TIME;
        return  Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .setExpiration(new Date(date.getTime() + time))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();

    }

    public void setCookie(String refreshToken,HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN,refreshToken);
        cookie.setMaxAge((int) REFRESH_TIME);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void removeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);


    }



    // 토큰 검증
    public Boolean tokenValidation(String token) throws ExpiredJwtException, TokenNotValidatException {
        System.out.println("token = " + token);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.SecurityException");
        } catch (ExpiredJwtException e) {
            log.error("잘못된 JWT 서명입니다.ExpiredJwtException");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT 서명입니다. IllegalArgumentException");
        }
        return false;
    }


    public Boolean refreshTokenValidation(String token) throws TokenNotValidatException {

        if (!tokenValidation(token)) return false;

        // DB에 저장한 토큰 비교
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(getEmailFromToken(token));

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String email) {
        System.out.println("email = " + email);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        // spring security 내에서 가지고 있는 객체입니다. (UsernamePasswordAuthenticationToken)
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 email 가져오는 기능
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰에서 id 가져오는 기능
    public Long getIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("id",Long.class);
    }


    // 어세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(ACCESS_TOKEN,BEARER+accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader(REFRESH_TOKEN,BEARER+refreshToken);
    }
}
