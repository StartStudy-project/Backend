package com.study.studyproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class reply extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String nickname;
    private String content;
    private String ip;

    private int depth; // 원글 0 : 댓글 :1
    private int ref; // 부모 ip 참조
    private boolean is_delete; // 삭제여뷰
    private LocalDateTime delete_at; // 삭제시간



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


    @Builder
    public reply(String nickname, String content, String ip, int depth, int ref,  Member user, Board board) {
        this.nickname = nickname;
        this.content = content;
        this.ip = ip;
        this.depth = 0;
        this.ref = 1;
        this.is_delete = false;
        this.user = user;
        this.board = board;
    }

    public void deleteReply() {
        this.is_delete = true;
    }

    //수정
    public void updateReply(String content) {
        this.content = content;
    }



}
