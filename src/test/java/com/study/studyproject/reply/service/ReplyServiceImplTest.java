package com.study.studyproject.reply.service;

import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Reply;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.login.dto.TokenDtoResponse;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.reply.dto.ReplyRequestDto;
import com.study.studyproject.reply.dto.UpdateReplyRequest;
import com.study.studyproject.reply.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.study.studyproject.entity.Category.CS;
import static com.study.studyproject.entity.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ReplyServiceImplTest {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ReplyServiceImpl replyService;
    @Autowired
    JwtUtil jwtUtil;


    @Test
    @DisplayName("게시글에 댓글 작성하다")
    void insertReply() {
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        Board boardCreate = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(boardCreate);


        TokenDtoResponse allToken = jwtUtil.createAllToken(member1.getEmail(), 1L);
        System.out.println("allToken = " + allToken);


        ReplyRequestDto replyOne = new ReplyRequestDto(boardCreate.getId(), null, "댓글 내용");

        //when
        replyService.insert(allToken.getRefreshToken(), replyOne);

        //then
        Reply reply = replyRepository.findById(boardCreate.getId()).get();

        assertThat(reply.getBoard()).isEqualTo(boardCreate);
        assertThat(reply.getContent()).isEqualTo(replyOne.getContent());
        assertThat(reply.getParent()).isNull();

    }


    @Test
    @DisplayName("사용자가 게시글에 대댓글 작성하다")
    void insertDuplReply() {
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        Board board = createBoard(member1, "제목1", "내용1",    "닉네임1", CS);
        Reply parentReply = createReply("첫댓글",null, member1,board) ; //1

        memberRepository.save(member1);
        boardRepository.save(board);
        replyRepository.save(parentReply);



        TokenDtoResponse allToken = jwtUtil.createAllToken(member1.getEmail(), 1L);
        ReplyRequestDto replyOne = new ReplyRequestDto(board.getId(), 1L, "댓글 내용");

        //when
        replyService.insert(allToken.getRefreshToken(), replyOne); //2

        //then
        Reply reply = replyRepository.findById(2L).get();
        System.out.println("reply = " + reply);

        assertThat(reply.getBoard()).isEqualTo(board);
        assertThat(reply.getContent()).isEqualTo(replyOne.getContent());
        assertThat(reply.getParent()).isNotNull();

    }




    @Test
    @DisplayName("게시글 댓글을 수정한다.")
    void updateReply() {

        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        memberRepository.save(member1);
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        boardRepository.save(board);

        Reply one = createReply("댓글1", null, member1, board);
        replyRepository.saveAll(List.of(one));

        UpdateReplyRequest updateReplyRequest = new UpdateReplyRequest(one.getId(), "수정된 내용1");

        //when
        replyService.updateReply(updateReplyRequest);

        //then
        Reply reply = replyRepository.findById(one.getId()).get();
        assertThat(reply).isEqualTo(one);
        assertThat(reply.getContent()).isEqualTo(updateReplyRequest.getContent());

    }

    @Test
    @DisplayName("사용자가 대댓글이 있는 댓글을 삭제할 때, 삭제되지 않고 상태만 변한다. ")
    void deleteParentReply() {

        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        memberRepository.save(member1);
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        boardRepository.save(board);

        Reply one = createReply("댓글1", null, member1, board);
        Reply two = createReply("대댓글1", one, member1, board);
        Reply tree = createReply("대댓글2", one, member1, board);
        Reply four = createReply("대댓글3", one, member1, board);

        two.updateParent(one);
        tree.updateParent(one);
        four.updateParent(one);


        replyRepository.save(one);


        //when
        replyService.deleteReply(one.getId());

        //then
        Reply reply = replyRepository.findById(one.getId()).get();
        assertThat(reply.getIsDeleted()).isTrue();

    }


    @Test
    @DisplayName("사용자가 대댓글을 삭제하면 삭제된다. ")
    void deleteChildReply() {

        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        memberRepository.save(member1);
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        boardRepository.save(board);

        Reply one = createReply("댓글1", null, member1, board);
        Reply two = createReply("대댓글1", one, member1, board);
        Reply tree = createReply("대댓글2", one, member1, board);
        Reply four = createReply("대댓글3", one, member1, board);

        two.updateParent(one);
        tree.updateParent(one);
        four.updateParent(one);


        replyRepository.save(one);


        //when
        replyService.deleteReply(two.getId());

        //then
        Reply reply = replyRepository.findById(two.getId()).orElseThrow();


    }

    private Reply createReply(
            String content, Reply parent,  Member member, Board board
    ) {
        return Reply.builder()
                .member(member)
                .parent(parent)
                .content(content)
                .board(board)
                .build();
    }
    private Member createMember
            (String email, String password, String username, String nickname) {
        {
            return com.study.studyproject.entity.Member.builder()
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