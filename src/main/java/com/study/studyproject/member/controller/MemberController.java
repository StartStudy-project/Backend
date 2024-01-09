package com.study.studyproject.member.controller;

import com.study.studyproject.board.dto.ListResponseDto;
import com.study.studyproject.board.dto.MainRequest;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.TokenNotValidatException;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import com.study.studyproject.member.dto.MemberListRequestDto;
import com.study.studyproject.member.dto.MemberUpdateResDto;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.member.service.MemberServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "마이페이지 기능", description = "사용자 마이페이지 기능 구현 ")
public class MemberController {


    private final MemberServiceImpl memberService;

    //사용자 정보 조회
    @Operation(summary = "사용자 정보 조회", description = "자신의 사용자 정보를 조회합니다..")
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponseDto> userInfo(@CookieValue(value = "Refresh_Token") String token) {
        return ResponseEntity.ok(memberService.userInfoService(token));
    }

    //수정
    @PatchMapping("/info/update")
    public ResponseEntity<GlobalResultDto> userInfoUpdate(@RequestBody MemberUpdateResDto memberUpdateResDto, @CookieValue(value = "Refresh_Token") String token) {
        return ResponseEntity.ok(memberService.userInfoUpdate(token, memberUpdateResDto));
    }


    @Operation(summary = "사용자 게시글 조회", description = "사용자 스터디 게시글 조회")
    @GetMapping("/list")
    public ResponseEntity<Page<ListResponseDto>> mainList(
            @CookieValue(value = "Refresh_Token") String token,
            @RequestBody MemberListRequestDto memberListRequestDto,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(memberService.listMember(token, memberListRequestDto, pageable));
    }



}
