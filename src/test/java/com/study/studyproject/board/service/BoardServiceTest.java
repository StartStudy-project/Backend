package com.study.studyproject.board.service;

import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
 BoardRepository boardRepository;

    @Autowired
     MemberRepository memberRepository;



    @Test
    void boardSave() {
    }

    @Test
    void updateWrite() {
    }

    @Test
    void list() {
    }
}