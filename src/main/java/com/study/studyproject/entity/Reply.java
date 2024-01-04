package com.study.studyproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Reply extends BaseTimeEntity{

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
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


    @Builder
    public Reply(String nickname, String content, String ip, int depth, int ref, Member member, Board board) {
        this.nickname = nickname;
        this.content = content;
        this.ip = ip;
        this.depth = 0;
        this.ref = 1;
        this.is_delete = false;
        this.member = member;
        this.board = board;
    }


    //삭제
    public void deleteReply() {
        this.is_delete = true;
    }

    //수정
    public void updateReply(String content) {
        this.content = content;
    }

    public void UpdateMember(Member member) {
        this.member = member; // 1
        member.getReplies().add(this);

    }
}
