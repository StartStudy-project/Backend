package com.study.studyproject.member.service;

import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Member;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.study.studyproject.entity.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;


    @Autowired
    AdminServiceImpl adminService;



    @Test
    @DisplayName("사용자 검색을 입력하지 않을 경우, 사용자 전체 정보 가져온다.")
    void UserAllAdmin() throws Exception {
        //given
        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임0");
        Member member2 = createMember("jacom1@naver.com", "!kimkimkim", "사용자명2", "닉네임1");
        Member member3 = createMember("jacom3@naver.com", "!123", "사용자명3", "닉네임2");
        Member member4 = createMember("jacom4@naver.com", "!1234", "사용자명4", "닉네임3");
        memberRepository.saveAll(List.of(member1,member2,member3,member4));

        PageRequest pageRequest = PageRequest.of(0, 10);
        //when

        Page<UserInfoResponseDto> userInfoResponseDtos = adminService.userInfoAll(null, pageRequest);

        //then
        List<UserInfoResponseDto> content = userInfoResponseDtos.getContent();
        assertThat(content.size()).isEqualTo(4);

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