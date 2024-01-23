package com.study.studyproject.board.service;

import com.study.studyproject.board.dto.BoardOneResponseDto;
import com.study.studyproject.board.dto.BoardReUpdateRequestDto;
import com.study.studyproject.board.dto.BoardWriteRequestDto;
import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.*;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.login.dto.TokenDtoResponse;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.reply.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.http.HttpResponse;
import java.util.List;

import static com.study.studyproject.entity.Category.CS;
import static com.study.studyproject.entity.Category.기타;
import static com.study.studyproject.entity.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
  BoardRepository boardRepository;

    @Autowired
     MemberRepository memberRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    JwtUtil jwtUtil;



    @Test
    @DisplayName("게시글 저장을 한다.")
    void boardSave(){
        //given
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        memberRepository.save(member1);

        BoardWriteRequestDto writeBoard= writeBoard(Category.CS, "닉네임");
        TokenDtoResponse allToken = jwtUtil.createAllToken("jacom2@naver.com", 1L);

        //when
        GlobalResultDto resultDto = boardService.boardSave(writeBoard, allToken.getRefreshToken());

        Board board = boardRepository.findById(1L).get();
        System.out.println("board = " + board.getTitle());


        //then
        assertThat(board.getNickname()).isEqualTo(writeBoard.getNickname());



    }

    @Test
    @DisplayName("사용자가 수정하고 싶은 게시글을 수정한다.")
    void updateWrite() {
        //given

        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        memberRepository.save(member1);


        Board boardCreate = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        boardRepository.save(boardCreate);
        TokenDtoResponse allToken = jwtUtil.createAllToken("jacom2@naver.com", member1.getId());

        BoardReUpdateRequestDto boardReUpdateRequestDto = upddateBoard(member1.getId(), "수정된 내용", Category.코테, "수정된 타이틀");

        //when
        boardService.updateWrite(boardReUpdateRequestDto);

        Board board = boardRepository.findById(1L).get();

        //then
        assertThat(board.getTitle()).isEqualTo(boardReUpdateRequestDto.getTitle());
        assertThat(board.getContent()).isEqualTo(boardReUpdateRequestDto.getContent());
        assertThat(board.getCategory()).isEqualTo(boardReUpdateRequestDto.getCategory());



    }

    @Test
    @DisplayName("댓글이 없는 게시글을 조회한다.")
    void selectBaordOne() {
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        memberRepository.save(member1);

        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        Board board1 = createBoard(member1, "제목2", "내용2", "닉네임1", CS);
        Board board2 = createBoard(member1, "제목3", "내용3", "닉네임1", 기타);


        List<Board> products = List.of(board, board1, board2);
        System.out.println("products = " + products);
        List<Board> boards = boardRepository.saveAll(products);
        System.out.println("boards = " + boards);

        TokenDtoResponse allToken = jwtUtil.createAllToken("jacom2@naver.com", 1L);


        BoardOneResponseDto boardOneResponseDto = boardService.boardOne( 1L);
        System.out.println("boardOneResponseDto = " + boardOneResponseDto);

        assertThat(boardOneResponseDto.getContent()).isEqualTo(board.getContent());
        assertThat(boardOneResponseDto.getTitle()).isEqualTo(board.getTitle());
    }


    @Test
    @DisplayName("댓글을 가지고 있는 게시글을 조회한다.")
    void selectBaordOnewithReplies() {
        //given
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);

        Reply reply = createReply(null, member1, board);
        Reply reply1 = createReply(reply, member1, board);
        Reply reply2 = createReply(reply, member1, board);
        Reply reply3 = createReply(reply, member1, board);

        Reply replyParent2 = createReply(null, member1, board);
        Reply replyChild1 = createReply(replyParent2, member1, board);
        Reply replyChild2 = createReply(replyParent2, member1, board);
        Reply replyChild3 = createReply(replyParent2, member1, board);


        memberRepository.save(member1);
        boardRepository.save(board);
        replyRepository.saveAll(List.of(reply,reply1, reply2,reply3,replyParent2,replyChild1,replyChild2,replyChild3));


        //when
        List<Reply> byBoardReply = replyRepository.findByBoardReplies(board.getId());
        TokenDtoResponse allToken = jwtUtil.createAllToken("jacom2@naver.com", 1L);

        //when
        BoardOneResponseDto boardOneResponseDto = boardService.boardOne( 1L);
        System.out.println("boardOneResponseDto = " + boardOneResponseDto);
        System.out.println(boardOneResponseDto.getReplyResponseDto());
        System.out.println(boardOneResponseDto.getReplyResponseDto().getReplies());

        assertThat(boardOneResponseDto.getReplyResponseDto().getGetTotal()).isEqualTo(4L);
        assertThat(boardOneResponseDto.getReplyResponseDto().getReplies().get(0).getChildren()).hasSize(3);

        assertThat(boardOneResponseDto.getContent()).isEqualTo(board.getContent());
        assertThat(boardOneResponseDto.getTitle()).isEqualTo(board.getTitle());
    }

    @Test
    @DisplayName("댓글이 있는 게시글을 삭제한다.")
    void deleteBoard() throws Exception {
        //given
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        memberRepository.save(member1);

        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        boardRepository.save(board);


        Reply reply = createReply(null, member1, board);
        replyRepository.save(reply);

        Reply reply1 = createReply(reply, member1, board);
        Reply reply2 = createReply(reply, member1, board);
        replyRepository.saveAll(List.of(reply1, reply2));


        //when
        boardService.boardDeleteOne(board.getId());
        List<Board> allBoard = boardRepository.findAll();

        //then
        Assertions.assertThat(allBoard).hasSize(0);

    }




    private Reply createReply(
             Reply parent,  Member member, Board board
    ) {
        return Reply.builder()
                .member(member)
                .parent(parent)
                .content("내용")
                .board(board)
                .build();
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


    BoardReUpdateRequestDto upddateBoard(
            Long boardId,  String content, Category category, String title
    ){
        return BoardReUpdateRequestDto.builder().
                boardId(boardId)
                .content(content)
                .category(category)
                .title(title)
                .build();
    }
    private BoardWriteRequestDto writeBoard(
              Category category, String nickname
    ) {
        return BoardWriteRequestDto.builder()
                .category(category)
                .nickname(nickname)
                .title("title")
                .content("내용")
                .nickname(nickname)
                .build();
    }
}