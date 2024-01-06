package com.study.studyproject.login.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String pwd;

    @Builder
    public LoginRequest(String id, String pwd) {
        this.email = id;
        this.pwd = pwd;
    }
}
