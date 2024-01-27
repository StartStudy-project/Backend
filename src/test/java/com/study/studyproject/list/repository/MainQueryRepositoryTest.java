//package com.study.studyproject.list.repository;
//
//import com.querydsl.jpa.impl.JPAQuery;
//import com.study.studyproject.list.dto.MainRequest;
//import com.study.studyproject.board.repository.BoardRepository;
//import com.study.studyproject.entity.*;
//import com.study.studyproject.list.dto.ListResponseDto;
//import com.study.studyproject.member.repository.MemberRepository;
//import com.study.studyproject.reply.repository.ReplyRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
//import java.util.List;
//
//import static com.study.studyproject.entity.Category.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.tuple;
//
//@SpringBootTest
//@Transactional
//class MainQueryRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Autowired
//    ReplyRepository replyRepository;
//
//    @Autowired
//    MainQueryRepository mainQueryRepository;
//
//
//
//    @BeforeEach
//    void init() {
//        memberRepository.save(Member.builder()
//                .email("jac")
//                .role(Role.ROLE_USER)
//                .password("1234").nickname("jacom")
//                .build());
//
//        Member ja2c = memberRepository.save(Member.builder()
//                .email("ja2c")
//                .role(Role.ROLE_USER)
//                .password("12324").nickname("jacom")
//                .build());
//
//        memberRepository.save(Member.builder()
//                .email("jac")
//                .role(Role.ROLE_USER)
//                .password("12334").nickname("jacom")
//                .build());
//        Board build = Board.builder()
//                .content("내용")
//                .title("하하")
//                .nickname("ac")
//                .category(CS)
//                .member(ja2c)
//                .build();
//
//        boardRepository.save(build);
//
//        Board build1 = Board.builder()
//                .content("내용")
//                .title("잎")
//                .nickname("ac")
//                .category(CS)
//                .member(ja2c)
//                .build();
//
//        boardRepository.save(build1);
//
//        Board build2 = Board.builder()
//                .content("내용")
//                .title("꿈나라")
//                .nickname("ac")
//                .category(CS)
//                .member(ja2c)
//                .build();
//        boardRepository.save(build2);
//
//
//        Board build3 = Board.builder()
//                .content("내용")
//                .title("제목")
//                .nickname("ac")
//                .category(CS)
//                .member(ja2c)
//                .build();
//        boardRepository.save(build3);
//
//    }
//
//
//    @Test
//    @DisplayName("메인페이지에 찾고자 하는 값을 검색하여 정보를 추출한다.")
//    void boardListPage() {
//        //given
//        MainRequest listRequestDto = new MainRequest(CS,1);
//        PageRequest pageRequest = PageRequest.of(0, 3);
//        String contents = "꿈나라";
//
//        //when
//        Page<ListResponseDto> listResponseDtos = mainQueryRepository.boardListPage(contents, listRequestDto, pageRequest);
//
//        List<ListResponseDto> content = listResponseDtos.getContent();
//
//        System.out.println("content = " + content);
//
//        //then
//        assertThat(content).extracting("content", "title", "nickname", "recurit")
//                .containsExactlyInAnyOrder(
//                        tuple(
//                        "내용", "꿈나라", "ac", "모집중"));
//
//
//    }
//
//
//    @Test
//    @DisplayName("메인페이지에 검색 값을 넣지 않고 정보를 추출한다.")
//    void getContent() {
//        MainRequest listRequestDto = new MainRequest(CS,1);
//        PageRequest pageRequest = PageRequest.of(0, 3);
//        String findParam = null;
//        List<ListResponseDto> content = mainQueryRepository.getContent(findParam,listRequestDto, pageRequest);
//        assertThat(content).hasSize(3);
//
//    }
//
//
//    @Test
//    @DisplayName("메인 페이지의 전체 개수를 가져온다.")
//    void getTotal() {
//        MainRequest listRequestDto = new MainRequest(CS,1);
//        String contents = "꿈나라";
//        JPAQuery<Board> total = mainQueryRepository.getTotal(contents, listRequestDto);
//        List<Board> fetch = total.fetch();
//        assertThat(fetch).hasSize(1);
//
//    }
//}