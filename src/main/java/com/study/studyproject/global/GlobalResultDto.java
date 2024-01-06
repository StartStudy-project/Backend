package com.study.studyproject.global;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalResultDto {
    private String message;
    private int statusCode;

    public GlobalResultDto(String msg, int statusCode) {
        this.message = msg;
        this.statusCode = statusCode;
    }

}
