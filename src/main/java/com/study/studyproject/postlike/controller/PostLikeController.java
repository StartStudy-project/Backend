package com.study.studyproject.postlike.controller;


import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.postlike.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/postLike/")
public class PostLikeController {

    private final JwtUtil jwtUtil;
    private final PostLikeService postLikeService;

    @Operation(summary = "관심글 등록", description = "사용자의 관심글 등록합니다.")
    @PostMapping("/save/{boardId}")
    public ResponseEntity<GlobalResultDto> postLikeSave(@PathVariable Long boardId, @RequestHeader("Access_Token") String token) {
        Long idFromToken = jwtUtil.getIdFromToken(jwtUtil.resolveToken(token));
        System.out.println("idFromToken = " + idFromToken);
        return ResponseEntity.ok(postLikeService.postLikeSave(boardId,idFromToken));
    }


    @Operation(summary = "관심글 삭제", description = "사용자의 관심글 삭제합니다.")
    @DeleteMapping("/{postLikeId}")
    public ResponseEntity<GlobalResultDto> postLikeDelete(@PathVariable(name = "postLikeId") Long postLikeId) {
        return ResponseEntity.ok(postLikeService.postLikeDelete(postLikeId));
    }



}
