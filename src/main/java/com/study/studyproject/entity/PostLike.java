package com.study.studyproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@NoArgsConstructor
@Getter
@Table(name = "postLike")
public class PostLike extends BaseTimeEntity{
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



}
