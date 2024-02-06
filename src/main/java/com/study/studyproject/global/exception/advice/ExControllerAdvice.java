package com.study.studyproject.global.exception.advice;

import com.study.studyproject.global.exception.ExceptionResponse;
import com.study.studyproject.global.exception.ex.TokenNotValidatException;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(BAD_REQUEST) //400
    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionResponse illegalExHandler(IllegalArgumentException e) {
        return new ExceptionResponse(BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> userExHandler(UserNotFoundException e) {
        ExceptionResponse errorResult = new ExceptionResponse(NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(errorResult, NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> tokenNotValidatException(TokenNotValidatException e) {
        ExceptionResponse errorResult = new ExceptionResponse(NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(errorResult, NOT_FOUND);
    }


    //500
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exception(Exception e) {
        System.out.println("e.getStackTrace() = " + e.getStackTrace());
        System.out.println("e = " + e);
        ExceptionResponse errorResult = new ExceptionResponse(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase());
        return new ResponseEntity<>(errorResult, INTERNAL_SERVER_ERROR);
    }



    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = { AccessDeniedException.class })
    protected ResponseEntity forbidden(AccessDeniedException e) {
        ExceptionResponse errorResult = new ExceptionResponse(FORBIDDEN.value(),e.getMessage());
        return new ResponseEntity<>(errorResult, FORBIDDEN);

    }


    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<ExceptionResponse> notFoundEx(NotFoundException e) {
        ExceptionResponse errorResult = new ExceptionResponse(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase());
        System.out.println("errorResult = " + errorResult);
        return new ResponseEntity<>(errorResult, NOT_FOUND);
    }



    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exhandle(IllegalStateException e) {
        ExceptionResponse errorResult = new ExceptionResponse(BAD_REQUEST.value(),e.getMessage());
        return new ResponseEntity<>(errorResult, BAD_REQUEST);
    }


    // Bean
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            body.put(fieldName, errorMessage);
        });



        log.info("Errors  {} :  ", errors);
        return new ResponseEntity<>(body, headers, status);
    }



}
