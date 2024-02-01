package com.study.studyproject.entity;

import com.study.studyproject.member.dto.MemberUpdateResDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@ToString(of = {"id", "email", "password","nickname","username","role"})
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email; // 아이디 
    private String password; //비밀번호

    private String username;
    private String nickname;


    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Board> board = new ArrayList<>();


    @Builder
    public Member(String email, String password, String username, String nickname, Role role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.role = role;
    }

    public Member updateMemberInfo(MemberUpdateResDto dto) {
        this.username = dto.getUsername();
        this.nickname = dto.getNickname();
        return this;
    }
}
