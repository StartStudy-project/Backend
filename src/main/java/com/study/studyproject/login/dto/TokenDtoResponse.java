package com.study.studyproject.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDtoResponse {

    private String accessToken;
    private String refreshToken;

}
