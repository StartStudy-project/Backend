package com.study.studyproject.login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;
    private String pwd;

    @Builder
    public LoginRequest(String id, String pwd) {
        this.email = id;
        this.pwd = pwd;
    }
}
