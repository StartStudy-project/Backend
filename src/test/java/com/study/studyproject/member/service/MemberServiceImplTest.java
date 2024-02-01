package com.study.studyproject.member.service;

import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.member.repository.MyPageQueryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MemberServiceImpl memberService;


    @Test
    void userInfoService() {




    }

    @Test
    void userInfoUpdate() {
    }

    @Test
    void listMember() {
    }
}