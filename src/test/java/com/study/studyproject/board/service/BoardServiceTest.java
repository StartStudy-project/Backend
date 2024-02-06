package com.study.studyproject.board.service;

import com.study.studyproject.board.dto.BoardChangeRecruitRequestDto;
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
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
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

        //when
        GlobalResultDto resultDto = boardService.boardSave(writeBoard, member1.getId());

        //then
        assertThat(resultDto.getMessage()).isEqualTo("글 작성 완료");



    }

    @Test
    @DisplayName("사용자가 수정하고 싶은 게시글을 수정한다.")
    void updateWrite() {
        //given

        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        memberRepository.save(member1);


        Board boardCreate = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        boardRepository.save(boardCreate);

        BoardReUpdateRequestDto boardReUpdateRequestDto = upddateBoard(boardCreate.getId(), "수정된 내용", Category.코테, "수정된 타이틀");

        //when
        boardService.updateWrite(boardReUpdateRequestDto);

        Board board = boardRepository.findById(boardCreate.getId()).get();

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
        List<Board> boards = boardRepository.saveAll(products);

        TokenDtoResponse allToken = jwtUtil.createAllToken("jacom2@naver.com", member1.getId());

        //when
        BoardOneResponseDto boardOneResponseDto = boardService.boardOne(board.getId(),allToken.getAccessToken());

        //then
        assertThat(boardOneResponseDto.getContent()).isEqualTo(board.getContent());
        assertThat(boardOneResponseDto.getTitle()).isEqualTo(board.getTitle());
    }


    @Test
    @DisplayName("자기 댓글을 가지고 있는 게시글을 조회한다.")
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
        List<Reply> replies = List.of(reply, reply1, reply2, reply3, replyParent2, replyChild1, replyChild2, replyChild3);
        replyRepository.saveAll(replies);


        //when
        List<Reply> byBoardReply = replyRepository.findByBoardReplies(board.getId());

        //when
        TokenDtoResponse allToken = jwtUtil.createAllToken("jacom2@naver.com", member1.getId());
        BoardOneResponseDto boardOneResponseDto = boardService.boardOne( board.getId(),allToken.getAccessToken());
        System.out.println("boardOneResponseDto = " + boardOneResponseDto);

        assertThat(boardOneResponseDto.getReplyResponseDto().getGetTotal()).isEqualTo(replies.size());
        assertThat(boardOneResponseDto.getReplyResponseDto().getReplies().get(0).getChildren()).hasSize(3);

        assertThat(boardOneResponseDto.getContent()).isEqualTo(board.getContent());
        assertThat(boardOneResponseDto.getTitle()).isEqualTo(board.getTitle());
    }



    @Test
    @DisplayName(" 사용자가 댓글 입력하지 않은 게시글을 조회한다.")
    void selectBaordOnewithNoMyReplies() {
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
        List<Reply> replies = List.of(reply, reply1, reply2, reply3, replyParent2, replyChild1, replyChild2, replyChild3);
        replyRepository.saveAll(replies);


        //when
        List<Reply> byBoardReply = replyRepository.findByBoardReplies(board.getId());

        //when
        BoardOneResponseDto boardOneResponseDto = boardService.boardOne( board.getId(),null);
        System.out.println("boardOneResponseDto = " + boardOneResponseDto);

        assertThat(boardOneResponseDto.getReplyResponseDto().getGetTotal()).isEqualTo(replies.size());
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

        Reply reply1 = createReply(reply, member1, board);
        Reply reply2 = createReply(reply, member1, board);
        replyRepository.save(reply);
        replyRepository.saveAll(List.of(reply1, reply2));


        //when
        GlobalResultDto globalResultDto = boardService.boardDeleteOne(board.getId());

        //then
        List<Board> allBoard = boardRepository.findAll();
        assertThat(allBoard).hasSize(1);
        assertThat(globalResultDto.getMessage()).isEqualTo("게시글을 삭제 할 수 없습니다.");

    }


    @Test
    @DisplayName("모집완료 버튼을 누르면 모집중에서 모집완료로 모집구분이 수정된다.")
    void changeRecruitTest() throws Exception {
        //given
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(board);

        BoardChangeRecruitRequestDto boardChangeRecruitRequestDto = new BoardChangeRecruitRequestDto(board.getId(), Recruit.모집완료);
        //when
        boardService.changeRecruit(boardChangeRecruitRequestDto);

        //then
        Board board1 = boardRepository.findById(board.getId()).get();
        assertThat(board1.getRecruit()).isEqualTo(Recruit.모집완료);

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