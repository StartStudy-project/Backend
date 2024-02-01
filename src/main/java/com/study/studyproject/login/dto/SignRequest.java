package com.study.studyproject.login.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignRequest {

    @NotEmpty(message = "이름을 입력해주세요")
    private String name;

    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String pwd;

    @NotEmpty(message = "비밀번호 확인을 입력해주세요")
    private String checkPwd;

}
