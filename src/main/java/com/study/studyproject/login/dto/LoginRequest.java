package com.study.studyproject.login.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class LoginRequest {

    @NotEmpty(message = "아이디를 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String pwd;


}
