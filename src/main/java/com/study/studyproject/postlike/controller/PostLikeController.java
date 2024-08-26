package com.study.studyproject.postlike.controller;


import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.auth.UserDetailsImpl;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.postlike.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/post-like/")
@Tag(name = "사용자 관심 글",description = "사용장 관심 글 기능 수정 및 삭제")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(summary = "관심글 등록", description = "사용자의 관심글 등록합니다.")
    @PostMapping("{boardId}")
    public ResponseEntity<GlobalResultDto> postLikeSave(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postLikeService.postLikeSave(boardId,userDetails.getMember()));
    }


    @Operation(summary = "관심글 삭제", description = "사용자의 관심글 삭제합니다.")
    @DeleteMapping("{postLikeId}")
    public ResponseEntity<GlobalResultDto> postLikeDelete(@PathVariable(name = "postLikeId") Long postLikeId) {
        return ResponseEntity.ok(postLikeService.postLikeDelete(postLikeId));
    }



}
