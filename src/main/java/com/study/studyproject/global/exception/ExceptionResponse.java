package com.study.studyproject.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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
}
