package com.study.studyproject.login.service;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.RefreshToken;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.login.repository.RefreshRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutService {

    private final RefreshRepository refreshRepository;


    //로그아웃
    public GlobalResultDto logoutService(String memberEmail) {
        Optional<RefreshToken> findEmail = refreshRepository.findByEmail(memberEmail);
        if (findEmail.isPresent()) {
            refreshRepository.delete(findEmail.get());
        }
        return new GlobalResultDto("로그아웃 되었습니다.", HttpStatus.OK.value());
    }
}
