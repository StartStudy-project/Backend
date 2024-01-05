package com.study.studyproject.global;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalResultDto {
    private String msg;
    private int statusCode;

    public GlobalResultDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

}
