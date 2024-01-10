package com.study.studyproject.member.controller;

import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.member.service.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    @GetMapping("/userAll")
    public Page<UserInfoResponseDto> userAllInfo(@PageableDefault(size = 10) Pageable pageable) {
        return adminService.userInfoAll(pageable);
    }
}
