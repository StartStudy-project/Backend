package com.study.studyproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;                                                          // 고유 ID

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;                                                    // Member 와 연관 관계 설정

    @Column
    private String title;                                                     // 포스트 타이틀

    @Column
    private String content;                                                   // 포스트 내용

    @Column
    private String nickname;                                                  // 작성자 닉네임

    @Column
    private Category category;                                                  // 카테고리


    @OneToMany(mappedBy = "post") //지연로딩
    private List<PostLike> postLikes;


}
