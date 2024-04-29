package com.study.studyproject.list.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.study.studyproject.list.dto.MainRequest;
import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.*;
import com.study.studyproject.list.dto.ListResponseDto;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.reply.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.study.studyproject.entity.Category.*;
import static com.study.studyproject.entity.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Transactional
class MainQueryRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MainQueryRepository mainQueryRepository;



    @Test
    @DisplayName("메인페이지에 찾고자 하는 값을 검색하여 정보를 추출한다.")
    void boardListPage() {


        //given
        Member member = createMember("jacom1@naver.com", "!112341234", "사용자명1", "닉네임1");
        Board board = createBoard(member, "꿈나라", "내용1", "닉네임1", CS);
        Board board1 = createBoard(member, "제목2", "내용2", "닉네임1", CS);
        Board board2 = createBoard(member, "제목3", "내용3", "닉네임1", 기타);
        memberRepository.save(member);
        boardRepository.saveAll(List.of(board, board1, board2));


        MainRequest listRequestDto = new MainRequest(CS,1);
        PageRequest pageRequest = PageRequest.of(0, 3);
        String title = "꿈나라";

        //when
        Page<ListResponseDto> listResponseDtos = mainQueryRepository.getBoardListPage(title, listRequestDto, pageRequest);

        List<ListResponseDto> content = listResponseDtos.getContent();



        //then
        assertThat(content).extracting("content", "title", "nickname", "recurit")
                .containsExactlyInAnyOrder(
                        tuple(
                        "내용", "꿈나라", "닉네임1", "모집중"));


    }


    @Test
    @DisplayName("메인페이지에 검색 값을 넣지 않고 정보를 추출한다.")
    void getContent() {
        Member member = createMember("jacom1@naver.com", "!112341234", "사용자명1", "닉네임1");
        Board board = createBoard(member, "꿈나라", "내용1", "닉네임1", CS);
        Board board1 = createBoard(member, "제목2", "내용2", "닉네임1", CS);
        Board board2 = createBoard(member, "제목3", "내용3", "닉네임1", 기타);
        memberRepository.save(member);
        boardRepository.saveAll(List.of(board, board1, board2));
        MainRequest listRequestDto = new MainRequest(CS,1);
        PageRequest pageRequest = PageRequest.of(0, 3);
        String findParam = null;

        //when
        List<ListResponseDto> content = mainQueryRepository.getContent(findParam,listRequestDto, pageRequest);

        //then
        assertThat(content).hasSize(2);

    }


    @Test
    @DisplayName("메인 페이지의 전체 개수를 가져온다.")
    void getTotal() {
        Member member = createMember("jacom1@naver.com", "!112341234", "사용자명1", "닉네임1");
        Board board = createBoard(member, "꿈나라", "내용1", "닉네임1", CS);
        Board board1 = createBoard(member, "제목2", "내용2", "닉네임1", CS);
        Board board2 = createBoard(member, "제목3", "내용3", "닉네임1", 기타);
        memberRepository.save(member);
        boardRepository.saveAll(List.of(board, board1, board2));
        MainRequest listRequestDto = new MainRequest(CS,1);
        String contents = "꿈나라";

        //when
        JPAQuery<Board> total = mainQueryRepository.getTotal(contents, listRequestDto);

        //thne
        List<Board> fetch = total.fetch();
        assertThat(fetch).hasSize(1);

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

    private Board createBoard(
            Member member, String title, String content, String nickname, Category category
    ) {
        return Board.builder()
                .member(member)
                .title(title)
                .content("내용")
                .nickname(nickname)
                .category(category)
                .build();
    }

}