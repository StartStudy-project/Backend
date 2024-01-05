package com.study.studyproject.login.service;

import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.RefreshToken;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.login.dto.LoginRequest;
import com.study.studyproject.login.dto.TokenDtoResponse;
import com.study.studyproject.login.repository.RefreshRepository;
import com.study.studyproject.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final RefreshRepository refreshRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;



    public GlobalResultDto loginService(LoginRequest loginRequest, HttpServletResponse response) {


        Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));

        if (!passwordEncoder.matches(loginRequest.getPwd(), member.getPassword())) {
            throw new UserNotFoundException("비밀번호가 일치하지 않습니다.");
        }

        TokenDtoResponse tokensDto = jwtUtil.createAllToken(loginRequest.getEmail());


        Optional<RefreshToken> refreshToken = refreshRepository.findByEmail(loginRequest.getEmail());

        if (refreshToken.isPresent()) {
            refreshRepository.save(refreshToken.get().updateToken(tokensDto.getRefreshToken()));
        } else {
            RefreshToken.builder()
                    .token(tokensDto.getRefreshToken())
                    .email(loginRequest.getEmail())
                    .build();
        }

        setHeader(response, tokensDto);

        return new GlobalResultDto("로그인 되었습니다.", HttpStatus.OK.value());

    }

    private void setHeader(HttpServletResponse response, TokenDtoResponse tokensDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokensDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokensDto.getRefreshToken());

    }


}