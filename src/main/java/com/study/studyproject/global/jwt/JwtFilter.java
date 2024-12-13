package com.study.studyproject.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.ErrorCode;
import com.study.studyproject.global.exception.ex.TokenNotValidationException;
import com.study.studyproject.login.domain.Role;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.study.studyproject.global.exception.ex.ErrorCode.TOKEN_EXPIRED;

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

        accessToken = jwtUtil.resolveToken(accessToken);
        if (accessToken == null) {
        }
        if (accessToken != null ) {//  회원일 경우,
            if (jwtUtil.AccessTokenValidation(accessToken)) { // AccessToken 사용 가능
                String emailFromToken = jwtUtil.getEmailFromToken(accessToken);
                setAuthentication(emailFromToken);
            } else {
                jwtExceptionHandler(response, ErrorCode.TOKEN_EXPIRED.getMessage(), HttpStatus.UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);




    }

    public void jwtExceptionHandler(HttpServletResponse response, String message, HttpStatus statusCode) {
        response.setStatus(statusCode.value());
        response.setContentType("application/json; charset=UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(new GlobalResultDto(message, statusCode.value()));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    private void setAuthentication(String email) {
        Authentication authentication = jwtUtil.createAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
