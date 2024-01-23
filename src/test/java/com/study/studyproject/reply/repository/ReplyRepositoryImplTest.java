package com.study.studyproject.reply.repository;

import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Reply;
import com.study.studyproject.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.study.studyproject.entity.Category.CS;
import static com.study.studyproject.entity.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReplyRepositoryImplTest {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;


    @Test
    void findByBoardReply() {
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        memberRepository.save(member1);
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        boardRepository.save(board);

        Reply one = createReply("댓글1", null, member1, board);
        Reply two = createReply("대댓글1", one, member1, board);
        Reply tree = createReply("대댓글2", one, member1, board);
        Reply four = createReply("대댓글3", one, member1, board);
        replyRepository.saveAll(List.of(one, two, tree, four));


        List<Reply> byBoardReply = replyRepository.findByBoardReply(board.getId());
        for (int i = 0; i < byBoardReply.size(); i++) {
            System.out.println("byBoardReply = " + byBoardReply.get(i).getBoard());
            System.out.println("byBoardReply = " + byBoardReply.get(i).getMember());
            System.out.println("byBoardReply = " + byBoardReply.get(i).getParent());
            System.out.println("byBoardReply = " + byBoardReply.get(i).getId());
            System.out.println();
        }


    }

    @Test
    @DisplayName("자식 먼저")
    void findByBoardReplies() throws Exception {
        //given
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        memberRepository.save(member1);
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        boardRepository.save(board);

        Reply one = createReply("댓글1", null, member1, board);
        Reply two = createReply("대댓글1", one, member1, board);
        Reply tree = createReply("대댓글2", one, member1, board);
        Reply four = createReply("대댓글3", one, member1, board);
        replyRepository.saveAll(List.of(one, two, tree, four));

        //when
        List<Reply> byBoardReply = replyRepository.findByBoardReplies(board.getId());
        //then

        for (int i = 0; i < byBoardReply.size(); i++) {
            System.out.println("byBoardReply = " + byBoardReply.get(i).getBoard());
            System.out.println("byBoardReply = " + byBoardReply.get(i).getMember());
            System.out.println("byBoardReply = " + byBoardReply.get(i).getParent());
            System.out.println("byBoardReply = " + byBoardReply.get(i).getId());
            System.out.println();
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

    private Reply createReply(
            String content, Reply parent,  Member member, Board board
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
}
