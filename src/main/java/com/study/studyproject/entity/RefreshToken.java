package com.study.studyproject.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {
    @Id
    private String id;
    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public RefreshToken(Member member, String token) {
        this.refreshToken = token;
        this.member = member;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}