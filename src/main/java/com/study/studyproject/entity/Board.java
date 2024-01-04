package com.study.studyproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;                                                          // 고유 ID

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;                                                    // Member 와 연관 관계 설정

    @Column
    private String title;                                                     // 포스트 타이틀

    private Long viewCount;     //조회수

    @Column
    private String content;                                                   // 포스트 내용

    @Column
    private String nickname;                                                  // 작성자 닉네임

    @Column
    private Category category;                                                  // 카테고리

    @OneToMany(mappedBy = "post") //지연로딩
    private List<PostLike> postLikes;

    @Builder
    public Board(Member member, String title, Long viewCount, String content, String nickname) {
        this.member = member;
        this.title = title;
        this.viewCount = viewCount;
        this.content = content;
        this.nickname = nickname;
        this.category = Category.모집중;
    }


    public Board update(String title, String content){
        this.title = title;
        this.content = content;
        return this;
    }





}
