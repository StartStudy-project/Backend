package com.study.studyproject.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.studyproject.domain.RefreshToken;
import com.study.studyproject.global.auth.UserDetailsServiceImpl;
import com.study.studyproject.global.exception.ex.TokenNotValidationException;
import com.study.studyproject.login.dto.TokenDtoResponse;
import com.study.studyproject.login.repository.RefreshRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.Key;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String BEARER = "Bearer ";
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshRepository refreshTokenRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final long ACCESS_TIME = 30 * 60 * 1000L;
    private static final long REFRESH_TIME =  24 * 60 * 60 * 1000L;
    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";




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

    private void setTokenResponse(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> result = new HashMap<>();
        result.put(ACCESS_TOKEN, accessToken);
        result.put(REFRESH_TOKEN, refreshToken);

    }



    // 토큰 생성
    public TokenDtoResponse createAllToken(String email, Long id) {
        String access = createToken(email, id, ACCESS_TOKEN);
        String refresh = createToken(email, id, REFRESH_TOKEN);
        return new TokenDtoResponse(access, refresh);
    }

    public String createToken(String email,Long id, String type) {

        Date date = new Date();

        long time = type.equals(ACCESS_TOKEN) ? ACCESS_TIME : REFRESH_TIME;

        return Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .setExpiration(new Date(date.getTime() + time))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
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


    // 토큰 검증
    public Boolean tokenValidation(String token) throws ExpiredJwtException, TokenNotValidationException {
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


    public Boolean refreshTokenValidation(String token) throws TokenNotValidationException {

        if (!tokenValidation(token)) return false;

        // DB에 저장한 토큰 비교
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccessToken(token);

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
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


    public  String resolveToken(String token) {

        if (StringUtils.hasText(token) && token.startsWith(BEARER)) {
            String[] split = token.split(" ");
            return split[1];
        }

        return null;
    }

}
