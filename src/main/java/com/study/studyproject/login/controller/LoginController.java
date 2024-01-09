package com.study.studyproject.login.controller;

import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.login.dto.LoginRequest;
import com.study.studyproject.login.service.LoginService;
import com.study.studyproject.login.service.LogoutService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final LogoutService logoutService;

    //회원가입
    @PostMapping("/sign")
    public ResponseEntity<GlobalResultDto> sign(@Validated @RequestBody com.study.studyproject.login.dto.SignRequest signRequest) {
        return ResponseEntity.ok(loginService.sign(signRequest));

    }



    @PostMapping("/login")
    public ResponseEntity<GlobalResultDto> login( @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        System.out.println("loginRequest = " + loginRequest);
        return ResponseEntity.ok(loginService.loginService(loginRequest, response));

    }

    @Operation(summary = "사용자 게시글 조회", description = "사용자 스터디 게시글 조회")
    @GetMapping("/logout")
    public void logout(@CookieValue(value = "Refresh_Token") String token, HttpServletResponse response) {
        logoutService.logoutService(token,response);
    }

}
