package com.study.studyproject.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 86400)
public class RefreshToken {
    @Id
    private String accessToken;

    @Indexed
    private String email;

    private String refreshToken;


    @Builder
    public RefreshToken(String email, String accessToken,String refreshToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.email = email;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }

}