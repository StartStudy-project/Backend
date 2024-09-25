package com.study.studyproject.login.service;

import com.study.studyproject.domain.Member;
import com.study.studyproject.domain.RefreshToken;
import com.study.studyproject.domain.Role;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.login.dto.LoginRequest;
import com.study.studyproject.login.dto.LoginResponseDto;
import com.study.studyproject.login.dto.SignRequest;
import com.study.studyproject.login.dto.TokenDtoResponse;
import com.study.studyproject.login.repository.RefreshRepository;
import com.study.studyproject.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.study.studyproject.global.exception.ex.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LoginService {

    private final RefreshRepository refreshRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;



    public LoginResponseDto loginService(@Valid LoginRequest loginRequest, HttpServletResponse response) throws NotFoundException {

        Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));

        if (!passwordEncoder.matches(loginRequest.getPwd(), member.getPassword())) {
            throw new NotFoundException(NOT_FOUND_PASSWORD);
        }

        TokenDtoResponse tokensDto = jwtUtil.createAllToken(loginRequest.getEmail(),member.getId());
        Optional<RefreshToken> refreshToken = refreshRepository.findByAccessToken(tokensDto.getAccessToken());
        if (refreshToken.isPresent()) {
            refreshRepository.save(refreshToken.get().updateToken(tokensDto.getRefreshToken()));
        } else {
            RefreshToken getRefreshToken = RefreshToken.toEntity(tokensDto, loginRequest);
            refreshRepository.save(getRefreshToken);
        }

        setHeader(response, tokensDto);

        return new LoginResponseDto("로그인 되었습니다.", HttpStatus.OK.value(),member.getNickname());

    }
    private void setHeader(HttpServletResponse response, TokenDtoResponse tokensDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, JwtUtil.BEARER+tokensDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, JwtUtil.BEARER+tokensDto.getRefreshToken());

    }


    //회원가입
    public GlobalResultDto sign(@Valid SignRequest signRequest) {
        validate(signRequest);
        String encodePwd = passwordEncoder.encode(signRequest.getPwd());
        Member member = Member.toEntity(signRequest, encodePwd);
        memberRepository.save(member);
        return new GlobalResultDto("회원가입 성공",HttpStatus.OK.value());

    }

    private void validate(SignRequest signRequest) {
        if (signRequest.isNotEqualsCheckPwd()) {
            throw new NotFoundException(NOT_FOUND_PASSWORD);
        }
        //중복 확인
        if (isPresentEmail(signRequest)) {
            throw new NotFoundException(MEMBER_DUPLICATED);
        }
    }

    private boolean isPresentEmail(SignRequest signRequest) {
        return memberRepository.findByEmail(signRequest.getEmail()).isPresent();
    }
}
