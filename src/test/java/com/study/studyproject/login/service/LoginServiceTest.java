package com.study.studyproject.login.service;

import com.study.studyproject.entity.Member;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import com.study.studyproject.login.dto.LoginRequest;
import com.study.studyproject.login.dto.LoginResponseDto;
import com.study.studyproject.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.study.studyproject.entity.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    HttpServletResponse response;

    @Test
    @DisplayName("사용자 이메일과 비밀번호가 일치했을 때, 로그인을 성공한다.")
    void loginServiceTest() {

        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임1");
        memberRepository.save(member1);

        LoginRequest loginRequest = new LoginRequest(member1.getEmail(), member1.getPassword());

        LoginResponseDto loginResponseDto = loginService.loginService(loginRequest, response);

        Assertions.assertThat(loginResponseDto.getMessage()).isEqualTo("로그인 되었습니다.");
        Assertions.assertThat(loginResponseDto.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(loginResponseDto.getNickname()).isEqualTo(member1.getNickname());

    }


    @Test
    @DisplayName("비밀번호가 틀렸을 때, 로그인을 실패한다.")
    void wrongPwdLoginServiceTest() {

        //given
        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임1");
        memberRepository.save(member1);

        LoginRequest loginRequest = new LoginRequest(member1.getEmail(), "wrongPwd");


        //when & then
        assertThatThrownBy(() -> loginService.loginService(loginRequest, response))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    

    private Member createMember
            (String email, String password, String username, String nickname) {
        {
            return Member.builder()
                    .nickname(nickname)
                    .username(username)
                    .email(email)
                    .password(password)
                    .role(ROLE_USER).build();
        }

    }
}