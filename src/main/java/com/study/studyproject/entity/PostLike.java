package com.study.studyproject.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
public class PostLike extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name = "postLike_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "post_id")
    private Post post;




}
