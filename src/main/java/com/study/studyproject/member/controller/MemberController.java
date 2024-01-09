package com.study.studyproject.member.controller;

import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.member.dto.MemberUpdateResDto;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.member.service.MemberServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {


    private final MemberServiceImpl memberService;

    //사용자 정보 조회
    @Operation(summary = "사용자 정보 조회",description = "자신의 사용자 정보를 조회합니다..")
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponseDto> userInfo(@CookieValue(value = "Refresh_Token") String token) {
        return ResponseEntity.ok(memberService.userInfoService(token));
    }

    //리스트
    @PatchMapping("/info/update")
    public ResponseEntity<GlobalResultDto> userInfoUpdate(@RequestBody MemberUpdateResDto memberUpdateResDto, @CookieValue(value = "Refresh_Token") String token) {
        return ResponseEntity.ok(memberService.userInfoUpdate(token, memberUpdateResDto));
    }



}
