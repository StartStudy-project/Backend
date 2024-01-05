package com.study.studyproject.login.dto;

import lombok.Getter;

@Getter
public class TokenDtoResponse {

    private String accessToken;
    private String refreshToken;


}
