package com.study.studyproject.login.controller;

import com.study.studyproject.login.dto.LoginRequest;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping
    public String login(LoginRequest loginRequest) {



    }
}
