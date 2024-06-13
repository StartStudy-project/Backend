package com.study.studyproject.reply.controller;

import com.study.studyproject.global.auth.UserDetailsImpl;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.reply.dto.ReplyRequestDto;
import com.study.studyproject.reply.dto.UpdateReplyRequest;
import com.study.studyproject.reply.service.ReplyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "댓글 기능 구현", description = "댓글 기능 ")
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyServiceImpl replyService;
    private final JwtUtil jwtUtil;

    @PostMapping
    @Operation(summary = "댓글 추가", description = "댓글 추가")
    public void insertReply(   @RequestHeader("Access_Token") String token
            , @RequestBody @Validated ReplyRequestDto replyRequestDto) {
        Long idFromToken = jwtUtil.getIdFromToken(jwtUtil.resolveToken(token));
        replyService.insert(idFromToken, replyRequestDto);

    }

    @PatchMapping
    @Operation(summary = "댓글 수정")
    public void update( @RequestBody @Validated UpdateReplyRequest updateReplyRequest) {
        replyService.updateReply(updateReplyRequest);
    }

    @DeleteMapping("/{rno}")
    @Operation(summary = "댓글 삭제")
    public void delete(@PathVariable("rno") Long rno) {
        replyService.deleteReply(rno);
    }

}

