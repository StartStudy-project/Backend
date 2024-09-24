package com.study.studyproject.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.studyproject.global.GlobalResultDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtUtil jwtUtil;


    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("로그인");

        String accessToken = jwtUtil.getHeaderToken(request, JwtUtil.ACCESS_TOKEN);
        String refreshToken = jwtUtil.getHeaderToken(request, JwtUtil.REFRESH_TOKEN);
        accessToken = resolveToken(accessToken);
        refreshToken = resolveToken(refreshToken);
        if (accessToken == null) {
            setAuthentication(null);
        }

        if (accessToken != null) {
            if (jwtUtil.tokenValidation(accessToken)) {
                String emailFromToken = jwtUtil.getEmailFromToken(accessToken);
                setAuthentication(emailFromToken);
            } else if (refreshToken != null) {
                Boolean isRefreshToken = jwtUtil.refreshTokenValidation(refreshToken);
                if (isRefreshToken) {
                    String emailFromToken = jwtUtil.getEmailFromToken(refreshToken);
                    Long idFromToken = jwtUtil.getIdFromToken(refreshToken);

                    String newAccessToken = jwtUtil.createToken(emailFromToken,idFromToken,jwtUtil.ACCESS_TOKEN);
                    jwtUtil.setHeaderAccessToken(response, newAccessToken);
                    setAuthentication(jwtUtil.getEmailFromToken(newAccessToken));
                } else {
                    jwtExceptionHandler(response, "만료된 JWT 서명입니다. IllegalArgumentException.", HttpStatus.BAD_REQUEST);
                    return;
                }
            }
        }



        filterChain.doFilter(request, response);


    }

    public void jwtExceptionHandler(HttpServletResponse response, String message, HttpStatus statusCode) {
        response.setStatus(statusCode.value());
        response.setContentType("application/json; charset=UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(new GlobalResultDto(message,statusCode.value()));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private  String resolveToken(String token) {

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            String[] split = token.split(" ");
            return split[1];
        }

        return null;
    }


    private void setAuthentication(String email) {
        Authentication authentication = jwtUtil.createAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
