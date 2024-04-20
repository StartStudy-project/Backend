package com.study.studyproject.entity;

import com.study.studyproject.board.dto.BoardChangeRecruitRequestDto;
import com.study.studyproject.board.dto.BoardOneResponseDto;
import com.study.studyproject.board.dto.BoardReUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@ToString(of = {"id", "title", "nickname","viewCount","content","category","recruit"})
public class Board extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;


    @ColumnDefault("0")
    private Long viewCount;

    private String content;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Recruit recruit;

    @ColumnDefault("FALSE")
    @Column(nullable = false)
    private Boolean isDeleted;


    @OneToMany(mappedBy = "board") //지연로딩
    private List<PostLike> postLikes;


    @OneToMany(mappedBy = "board") //지연로딩
    private List<Reply> replies;

    public void ChangeBoardIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void deleteBoardContent(String title,String content) {
        this.title = title;
        this.content = content;

    }


    @Builder
    public Board(Member member, String title, String content, String nickname, Category category) {
        this.member = member;
        this.title = title;
        this.viewCount = 0L;
        this.content = content;
        this.nickname = nickname;
        this.category = category;
        this.recruit = Recruit.모집중;
        this.isDeleted = false;

    }

    public Board updateBoard(BoardReUpdateRequestDto boardReUpdateRequestDto){
        this.title = boardReUpdateRequestDto.getTitle();
        this.content = boardReUpdateRequestDto.getContent();
        this.category = boardReUpdateRequestDto.getCategory();
        return this;
    }

    public Board changeRecuritBoard(BoardChangeRecruitRequestDto boardChangeRecruitRequestDto){
        this.recruit = boardChangeRecruitRequestDto.getRecruit();
        return this;
    }



    //조회수 증가
    public Board updateViewCnt(Long viewCount){
        this.viewCount = viewCount+1;
        return this;
    }








}
