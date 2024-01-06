package com.study.studyproject.global.exception.advice;

import com.study.studyproject.global.exception.ExceptionResponse;
import com.study.studyproject.global.exception.ex.TokenNotValidatException;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionResponse illegalExHandler(IllegalArgumentException e) {
        return new ExceptionResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> userExHandler(UserNotFoundException e) {
        ExceptionResponse errorResult = new ExceptionResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> tokenNotValidatException(TokenNotValidatException e) {
        ExceptionResponse errorResult = new ExceptionResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.NOT_FOUND);
    }


    //500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exception(Exception e) {
        ExceptionResponse errorResult = new ExceptionResponse(LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return new ResponseEntity<>(errorResult, HttpStatus.NOT_FOUND);
    }

    //404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exhandle(NotFoundException e) {
        ExceptionResponse errorResult = new ExceptionResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(),e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ExceptionResponse> forbidden(Forbidden e) {
        ExceptionResponse errorResult = new ExceptionResponse(LocalDateTime.now(),HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
        return new ResponseEntity<>(errorResult, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionResponse> unauthorized(Unauthorized e) {
        ExceptionResponse errorResult = new ExceptionResponse(LocalDateTime.now(),HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        return new ResponseEntity<>(errorResult, HttpStatus.UNAUTHORIZED);
    }




}
