package com.study.studyproject.entity;

import com.study.studyproject.board.dto.BoardOneResponseDto;
import com.study.studyproject.reply.dto.ReplyRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString(of = {"id","content"})
public class Reply extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Reply parent;


    @ColumnDefault("FALSE")
    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Reply> children = new ArrayList<>();



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Reply(String content, Reply parent, Member member, Board board) {
        this.content = content;
        this.parent = parent;
        this.isDeleted = false;
        this.member = member;
        this.board = board;
    }




    public static Reply toEntity(ReplyRequestDto replyRequestDto, Board board, Member member) {
        return Reply.builder()
                .content(replyRequestDto.getContent())
                .member(member)
                .board(board)
                .build();
    }

    public void updateParent(Reply parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }

    //수정
    public void updateReply(String content) {
        this.content = content;
    }

    public void updateWriter(Member member) {
        this.member = member;
        member.getReplies().add(this);
    }

    public void UpdateBoard(Board board) {
        this.board = board;
        if (board.getReplies() != null) {
            board.getReplies().add(this);
        }
    }

    public void ChangeIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
