package com.study.studyproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;


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

}
