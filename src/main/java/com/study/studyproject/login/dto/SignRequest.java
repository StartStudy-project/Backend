package com.study.studyproject.login.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String pwd;

    @NotEmpty
    private String checkPwd;

}
