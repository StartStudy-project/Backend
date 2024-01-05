package com.study.studyproject.login.controller;

import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.login.dto.LoginRequest;
import com.study.studyproject.login.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @GetMapping
    public ResponseEntity<GlobalResultDto> login(@Validated @RequestBody LoginRequest loginRequest, HttpServletResponse response) {


        return ResponseEntity.ok(loginService.loginService(loginRequest,response));


    }
}
