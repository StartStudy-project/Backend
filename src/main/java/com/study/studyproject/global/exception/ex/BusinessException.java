package com.study.studyproject.global.exception.ex;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    public BusinessException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
