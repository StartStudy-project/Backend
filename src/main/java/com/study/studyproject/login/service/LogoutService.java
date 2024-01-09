package com.study.studyproject.login.service;

import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.login.repository.RefreshRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;


    //로그아웃
    public void logoutService(String token, HttpServletResponse response) {
        String emailFromToken = jwtUtil.getEmailFromToken(token);
        refreshRepository.deleteByEmail(emailFromToken);
        jwtUtil.removeCookie(response);


    }
}
