package com.study.studyproject.login.controller;

import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.login.dto.LoginRequest;
import com.study.studyproject.login.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/sign")
    public ResponseEntity<GlobalResultDto> sign(@Validated @RequestBody com.study.studyproject.login.dto.SignRequest signRequest) {
        return ResponseEntity.created(URI.create("/login")).body(loginService.sign(signRequest));

    }



    @PostMapping("/login")
    public ResponseEntity<GlobalResultDto> login(@Validated @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        return ResponseEntity.ok(loginService.loginService(loginRequest, response));


    }
}
