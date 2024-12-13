package com.study.studyproject.postlike.domain;

import com.study.studyproject.board.domain.Board;
import com.study.studyproject.global.config.BaseTimeEntity;
import com.study.studyproject.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@NoArgsConstructor
@Getter
@Table(name = "postLike")
public class PostLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postLike_id")
    private Long id;

    @ManyToOne(fetch = LAZY) //지연로딩
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY) //지연로딩
    @JoinColumn(name = "board_id")
    private Board board;


    @Builder
    public PostLike(Member member, Board board) {
        this.member = member;
        this.board = board;
    }

    public static PostLike create(Member member, Board board) {
        return PostLike.builder()
                .member(member)
                .board(board)
                .build();
    }

    public static boolean isPostLikes(List<PostLike> postLikes) {
        return postLikes.size() != 0;
    }




}
