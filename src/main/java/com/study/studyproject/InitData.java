package com.study.studyproject;

import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.*;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.postlike.repository.PostLikeRepository;
import com.study.studyproject.reply.repository.ReplyRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Profile({"local","prod"})
public class InitData {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReplyRepository replyRepository;


    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @PostConstruct
    void init() {

        String encode = passwordEncoder.encode("1234");
        Member memberOne = Member.builder()
                .role(Role.ROLE_USER)
                .username("김하임")
                .email("kimSky@naver.com")
                .nickname("kimSky")
                .password(encode)
                .build();

        memberRepository.save(memberOne);

        Member membertwo = Member.builder()
                .role(Role.ROLE_ADMIN)
                .username("김일우")
                .email("admin@naver.com")
                .nickname("admin")
                .password(encode)
                .build();
        memberRepository.save(membertwo);

        Member member = memberRepository.findById(1L).get();
        Member membertree = Member.builder()
                .role(Role.ROLE_USER)
                .username("김지우")
                .email("huj@naver.com")
                .nickname("huj")
                .password(encode)
                .build();

        memberRepository.save(membertree);

        Category[] arr  = {
                Category.CS,Category.기타,Category.코테
        };



        for (int i = 1; i <= 15; i++) {
            int val = (int) (Math.random() * 3);
            Board build = Board.builder()
                    .content("같이 " + arr[val] + " 같이해요")
                    .nickname(member.getNickname())
                    .category(arr[val])
                    .title("같이 하실 " + arr[val]+" 하실 분?")
                    .member(member)
                    .build();
            boardRepository.save(build);
        }

        String[] regin = {"서울,인천,경기"};

        for (int i = 1; i <= 15; i++) {
            int val = (int) (Math.random() * 3);
            Board build = Board.builder()
                    .content(regin[val]+"에서 " + arr[val] + "같이해요")
                    .nickname(member.getNickname())
                    .category(arr[val])
                    .title(regin[val]+"에서 같이 " + arr[val]+"하실 분? ")
                    .member(member)
                    .build();
            boardRepository.save(build);
        }

        Board board = boardRepository.findById(25L).get();
        Reply reply = null;
        for (int i = 0; i < 2; i++) {
            reply = Reply.builder()
                    .board(board)
                    .member(member)
                    .content("답글" + (i + 1))
                    .build();

            Reply replyer = Reply.builder()
                    .member(memberOne)
                    .content("대댓글-1")
                    .parent(reply)
                    .board(board)
                    .build();

            replyer.updateParent(reply);
            replyRepository.save(reply);
            replyRepository.save(replyer);

        }

        for (int i = 0; i < 3; i++) {
            Board getBoard = boardRepository.findById((long) (i+4)).get();
            PostLike postLike = PostLike.create(member, getBoard);
            postLikeRepository.save(postLike);
        }

        for (int i = 0; i < 3; i++) {
            Board getBoard = boardRepository.findById((long) (i)).get();
            PostLike postLike = PostLike.create(membertree, getBoard);
            postLikeRepository.save(postLike);
        }




    }

}
