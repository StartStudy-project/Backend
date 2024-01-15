package com.study.studyproject.reply.controller;

import com.study.studyproject.global.auth.UserDetailsImpl;
import com.study.studyproject.reply.dto.ReplyRequestDto;
import com.study.studyproject.reply.service.ReplyServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "댓글 기능 구현", description = "댓글 기능 ")
public class ReplyController {

    private final ReplyServiceImpl replyService;

    @PostMapping("/insertReply")
    public void insertReply(@CookieValue(value = "Refresh_Token") String token, ReplyRequestDto replyRequestDto) {
        replyService.insert(token, replyRequestDto);

    }

    @PatchMapping("/updateReply")
    public void update(Long boardId, String content) {
        replyService.updateReply(boardId, content);
    }

    @DeleteMapping("/deleteReply")
    public void delete(Long bno) {
        replyService.deleteReply(bno);
    }


}

