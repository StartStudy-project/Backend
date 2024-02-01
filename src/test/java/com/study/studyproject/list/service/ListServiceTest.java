package com.study.studyproject.list.service;

import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Member;
import com.study.studyproject.list.dto.ListResponseDto;
import com.study.studyproject.list.dto.MainRequest;
import com.study.studyproject.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.study.studyproject.entity.Category.CS;
import static com.study.studyproject.entity.Category.기타;
import static com.study.studyproject.entity.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ListServiceTest {

    @Autowired
    ListService listService;
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;
    @Test
    @DisplayName("타이틀 검색을 입력하지 않고 CS카테고리와 최신순으로 전체 게시글을 메인 화면을 조회한다.")
    void mainListService() throws Exception {
        //given
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        memberRepository.save(member1);

        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        Board board1 = createBoard(member1, "제목2", "내용2", "닉네임2", CS);
        Board board2 = createBoard(member1, "자바공부 풀 사람1", "내용3", "닉네임3", 기타);

        List<Board> products = List.of(board, board1, board2);
        List<Board> boards = boardRepository.saveAll(products);

        MainRequest mainRequest = new MainRequest(CS, 0);
        PageRequest pageRequest = PageRequest.of(0, 3);

        //when
        Page<ListResponseDto> list = listService.list(null, mainRequest, pageRequest);

        //then
        List<ListResponseDto> boardList = list.getContent();
        assertThat(boardList).hasSize(2)
                .extracting("title","content", "nickname","type")
                .containsExactlyInAnyOrder(
                        tuple("제목1", "내용1","닉네임1","CS"),
                        tuple("제목2", "내용2","닉네임2","CS")
                );

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
                .content(content)
                .nickname(nickname)
                .category(category)
                .build();
    }




}