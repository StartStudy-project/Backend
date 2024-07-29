package com.study.studyproject.login.service;

import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.RefreshToken;
import com.study.studyproject.entity.Role;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LoginService {

    private final RefreshRepository refreshRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;



    public LoginResponseDto loginService(@Valid LoginRequest loginRequest, HttpServletResponse response) {

        Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));

        if (!passwordEncoder.matches(loginRequest.getPwd(), member.getPassword())) {
            throw new NotFoundException("비밀번호가 일치하지 않습니다.");
        }

        TokenDtoResponse tokensDto = jwtUtil.createAllToken(loginRequest.getEmail(),member.getId());

        log.info("로그인 Token");
        Optional<RefreshToken> refreshToken = refreshRepository.findByEmail(loginRequest.getEmail());
        log.info("로그인 find Token : {}",refreshToken );

        if (refreshToken.isPresent()) {
            refreshRepository.save(refreshToken.get().updateToken(tokensDto.getRefreshToken()));
            log.info("로그인1 find Token : {}",refreshToken );
        } else {
            RefreshToken getRefreshToken = RefreshToken.builder()
                    .token(tokensDto.getRefreshToken())
                    .email(loginRequest.getEmail())
                    .build();
            refreshRepository.save(getRefreshToken);
            log.info("로그인2  : {}",refreshToken );

        }

        setHeader(response, tokensDto);

        return new LoginResponseDto("로그인 되었습니다. : "+refreshToken.get(), HttpStatus.OK.value(),member.getNickname());

    }
    private void setHeader(HttpServletResponse response, TokenDtoResponse tokensDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, JwtUtil.BEARER+tokensDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, JwtUtil.BEARER+tokensDto.getRefreshToken());

    }


    //회원가입
    public GlobalResultDto sign(@Valid SignRequest signRequest) {
        if (!signRequest.getPwd().equals(signRequest.getCheckPwd())) {
            throw new NotFoundException("비밀번호가 틀립니다.");
        }


        //중복 확인
        if (memberRepository.findByEmail(signRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 회원가입 하였습니다.");
        }


        String[] splitEmail = signRequest.getEmail().split("@");

        String encodePwd = passwordEncoder.encode(signRequest.getPwd());
        Member member = Member.builder().role(Role.ROLE_USER)
                .username(signRequest.getName())
                .nickname(splitEmail[0])
                .password(encodePwd)
                .email(signRequest.getEmail()).build();

        memberRepository.save(member);
        
        return new GlobalResultDto("회원가입 성공",HttpStatus.OK.value());

    }
}
