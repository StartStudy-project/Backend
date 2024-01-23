package com.study.studyproject.entity;

import com.study.studyproject.board.dto.BoardChangeRecruitRequestDto;
import com.study.studyproject.board.dto.BoardOneResponseDto;
import com.study.studyproject.board.dto.BoardReUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;                                                          // 고유 ID

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;                                                    // Member 와 연관 관계 설정

    private String title;                                                     // 포스트 타이틀


    @ColumnDefault("0")
    private Long viewCount;

    private String content;                                                   // 포스트 내용

    private String nickname;                                                  // 작성자 닉네임

    @Enumerated(EnumType.STRING)
    private Category category;                                                  // 카테고리

    @Enumerated(EnumType.STRING)
    private Recruit recruit;


    @OneToMany(mappedBy = "board") //지연로딩
    private List<PostLike> postLikes;


    @OneToMany(mappedBy = "board") //지연로딩
    private List<Reply> replies;





    @Builder
    public Board(Member member, String title, String content, String nickname, Category category) {
        this.member = member;
        this.title = title;
        this.viewCount = 0L;
        this.content = content;
        this.nickname = nickname;
        this.category = category;
        this.recruit = Recruit.모집중;
    }

    public Board updateBoard(BoardReUpdateRequestDto boardReUpdateRequestDto){
        this.title = boardReUpdateRequestDto.getTitle();
        this.content = boardReUpdateRequestDto.getContent();
        this.category = boardReUpdateRequestDto.getCategory();
        return this;
    }

    public Board ChangeRecuritBoard(BoardChangeRecruitRequestDto boardChangeRecruitRequestDto){
        this.recruit = boardChangeRecruitRequestDto.getRecruit();
        return this;
    }



    //조회수 증가
    public Board updateViewCnt(Long viewCount){
        this.viewCount = viewCount+1;
        return this;
    }








}
