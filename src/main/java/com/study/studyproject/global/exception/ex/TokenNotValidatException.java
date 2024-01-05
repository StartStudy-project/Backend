package com.study.studyproject.global.exception.ex;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenNotValidatException extends Throwable {
    public TokenNotValidatException(String s, JwtException e) {
    }
}

