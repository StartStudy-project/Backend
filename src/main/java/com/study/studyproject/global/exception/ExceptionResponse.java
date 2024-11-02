package com.study.studyproject.global.exception;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.study.studyproject.global.exception.ex.ErrorCode.INTERNAL_SEVER_ERROR;

@Data
@NoArgsConstructor
public class ExceptionResponse {
    private int status;
    private String message;

    @Builder
    public ExceptionResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ExceptionResponse of(int status, String message) {
        return ExceptionResponse.builder()
                .message(message)
                .status(status)
                .build();
    }
}
