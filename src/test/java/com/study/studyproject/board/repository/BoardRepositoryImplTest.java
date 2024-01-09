package com.study.studyproject.board.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.study.studyproject.board.dto.ListRequestDto;
import com.study.studyproject.board.dto.ListResponseDto;
import com.study.studyproject.board.dto.MainRequest;
import com.study.studyproject.entity.*;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.reply.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import jdk.jfr.StackTrace;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class BoardRepositoryImplTest {

    @Autowired
     BoardRepositoryImpl boardRepositoryImpl;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ReplyRepository replyRepository;


    @BeforeEach
    void init() {
        memberRepository.save(Member.builder()
                .email("jac")
                .role(Role.ROLE_USER)
                .password("1234")
                .build());

        Member ja2c = memberRepository.save(Member.builder()
                .email("ja2c")
                .role(Role.ROLE_USER)
                .password("12324")
                .build());

        memberRepository.save(Member.builder()
                .email("jac")
                .role(Role.ROLE_USER)
                .password("12334")
                .build());
        Board build = Board.builder()
                .content("내용")
                .title("제목")
                .nickname("ac")
                .category(Category.CS)
                .member(ja2c)
                .build();

        boardRepository.save(build);

        Board build1 = Board.builder()
                .content("내용")
                .title("제목")
                .nickname("ac")
                .category(Category.CS)
                .member(ja2c)
                .build();

        boardRepository.save(build1);

        Board build2 = Board.builder()
                .content("내용")
                .title("제목")
                .nickname("ac")
                .category(Category.CS)
                .member(ja2c)
                .build();
        boardRepository.save(build2);


        Board build3 = Board.builder()
                .content("내용")
                .title("제목")
                .nickname("ac")
                .category(Category.CS)
                .member(ja2c)
                .build();
        boardRepository.save(build3);


//        Reply d = Reply.builder()
//                .board(build1)
//                .content("d")
//                .build();
//
//        replyRepository.save(d);
//
//
//        Reply d2 = Reply.builder()
//                .board(build)
//                .content("d")
//                .build();
//
//        replyRepository.save(d2);

    }




    @Test
    void getContent() {


        ListRequestDto listRequestDto = new MainRequest(Recruit.모집중, Category.CS,1);
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<ListResponseDto> content = boardRepositoryImpl.getContent(listRequestDto, pageRequest);
        assertThat(content).hasSize(3);

    }


    @Test
    void getTotal() {
        ListRequestDto listRequestDto = new MainRequest(Recruit.모집중, Category.CS,1);
        PageRequest pageRequest = PageRequest.of(0, 3);
        JPAQuery<Board> total = boardRepositoryImpl.getTotal(listRequestDto);
        List<Board> fetch = total.fetch();
        assertThat(fetch).hasSize(4);

    }

    }