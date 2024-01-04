package com.study.studyproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private Role role;

    @OneToMany(mappedBy = "member")
    private List<PostLike> postLikes = new ArrayList<>();


    public void setPostLikes(List<PostLike> postLikes) {
        this.postLikes = postLikes;
    }
}
