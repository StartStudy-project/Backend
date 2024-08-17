package com.study.studyproject.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.studyproject.global.exception.ExceptionResponse;
import com.study.studyproject.global.exception.ex.ErrorCode;
import com.study.studyproject.global.exception.ex.ForbiddenException;
import com.study.studyproject.global.exception.ex.TokenNotValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper; // For JSON serialization

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.info("--JwtAuthenticationEntryPoint --");
        ExceptionResponse errorResponse = ExceptionResponse.builder()
                .message(ErrorCode.UNABLE_ACCESS.getMessage())
                .status(ErrorCode.UNABLE_ACCESS.getStatus().value())
                .build();
        // Set response properties
        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonErrorResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonErrorResponse);

    }
}