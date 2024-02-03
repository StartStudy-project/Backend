package com.study.studyproject.postlike.controller;


import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.postlike.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final JwtUtil jwtUtil;
    private final PostLikeService postLikeService;

    @Operation(summary = "관심글 등록", description = "사용자의 관심글 등록합니다.")
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponseDto> postLikeSave(@RequestHeader("Access_Token") String token) {
        Long idFromToken = jwtUtil.getIdFromToken(jwtUtil.resolveToken(token));
        return ResponseEntity.ok(postLikeService.postLikeSave(idFromToken));
    }






}
