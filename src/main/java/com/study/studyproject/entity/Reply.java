package com.study.studyproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Reply extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String nickname;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Reply parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Reply> children = new ArrayList<>();


    private boolean is_delete; // 삭제여뷰
    private LocalDateTime delete_at; // 삭제시간



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


    @Builder
    public Reply(String nickname, String content, Reply parent, List<Reply> children, boolean is_delete, LocalDateTime delete_at, Member member, Board board) {
        this.nickname = nickname;
        this.content = content;
        this.parent = parent;
        this.children = children;
        this.is_delete = is_delete;
        this.delete_at = delete_at;
        this.member = member;
        this.board = board;
    }

    @Builder


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
