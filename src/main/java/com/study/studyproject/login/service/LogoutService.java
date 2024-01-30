package com.study.studyproject.login.service;

import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.login.repository.RefreshRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;


    //로그아웃
    public void logoutService(String token, HttpServletResponse response) {
        Long idFromToken = jwtUtil.getIdFromToken(token);
        refreshRepository.deleteById(idFromToken);
        jwtUtil.removeCookie(response);

    }
}
