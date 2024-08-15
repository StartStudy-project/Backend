package com.study.studyproject.global.exception.ex;

import com.study.studyproject.global.exception.ex.ForbiddenException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TokenNotValidationException extends ForbiddenException {
    public TokenNotValidationException(ErrorCode errorCode) {
        super(errorCode);
    }
}

