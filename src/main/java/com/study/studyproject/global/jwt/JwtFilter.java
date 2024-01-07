package com.study.studyproject.global.jwt;

import com.study.studyproject.global.exception.ex.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtUtil.getHeaderToken(request, JwtUtil.ACCESS_TOKEN);
        String refreshToken = jwtUtil.getHeaderToken(request, JwtUtil.REFRESH_TOKEN);

        if (jwtUtil.tokenValidation(accessToken)) {
            setAuthentication(jwtUtil.getEmailFromToken(accessToken));
        } else if (refreshToken != null) {
            Boolean isRefreshToken = jwtUtil.refreshTokenValidation(refreshToken);

            if (isRefreshToken) {
                String loginId = jwtUtil.getEmailFromToken(refreshToken);
                String newAccessToken = jwtUtil.creatAccessToken(loginId);
                jwtUtil.setHeaderAccessToken(response, newAccessToken);
                setAuthentication(jwtUtil.getEmailFromToken(newAccessToken));
            } else {
                throw new JwtException("RefreshToken Expired");
            }

        }


        filterChain.doFilter(request, response);



    }

    private void setAuthentication(String email) {
        Authentication authentication = jwtUtil.createAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
