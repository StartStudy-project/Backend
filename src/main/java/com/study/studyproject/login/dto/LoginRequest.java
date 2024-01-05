package com.study.studyproject.login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    private String id;
    private String pwd;

    @Builder
    public LoginRequest(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }
}
