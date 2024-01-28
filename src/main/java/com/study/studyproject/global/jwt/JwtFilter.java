package com.study.studyproject.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.studyproject.entity.QMember;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.JwtException;
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


    private final JwtUtil jwtUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtUtil.getHeaderToken(request, JwtUtil.ACCESS_TOKEN);
        String refreshToken = jwtUtil.getHeaderToken(request, JwtUtil.REFRESH_TOKEN);
        accessToken = resolveToken(accessToken);
        refreshToken = resolveToken(refreshToken);

        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);


        if (accessToken != null) {
            if (jwtUtil.tokenValidation(accessToken)) {
                String emailFromToken = jwtUtil.getEmailFromToken(accessToken);
                System.out.println("emailFromToken = " + emailFromToken);
                setAuthentication(emailFromToken);
            } else if (refreshToken != null) {
                Boolean isRefreshToken = jwtUtil.refreshTokenValidation(refreshToken);
                if (isRefreshToken) {
                    String loginId = jwtUtil.getEmailFromToken(refreshToken);
                    String newAccessToken = jwtUtil.creatAccessToken(loginId);
                    jwtUtil.setHeaderAccessToken(response, newAccessToken);
                    setAuthentication(jwtUtil.getEmailFromToken(newAccessToken));
                } else {
                    log.info("jwt 안됨");
                    throw new JwtException("RefreshToken Expired");
                }
            }
        }



        filterChain.doFilter(request, response);


    }
    private String resolveToken(String token) {

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
