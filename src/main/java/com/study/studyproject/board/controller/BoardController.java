package com.study.studyproject.board.controller;

import com.study.studyproject.board.dto.BoardReUpdateRequestDto;
import com.study.studyproject.board.dto.BoardWriteRequestDto;
import com.study.studyproject.board.service.BoardService;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.auth.UserDetailsImpl;
import com.study.studyproject.global.auth.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    BoardService boardService;

    //글쓰기 작성
    @PatchMapping("/writing")
    public String updateWriting(BoardReUpdateRequestDto boardReUpdateRequestDto ) {

        boardService.updateWrite(boardReUpdateRequestDto);
        return "";
    }

    @PostMapping("/writing")
    public ResponseEntity<GlobalResultDto> writing( BoardWriteRequestDto boardWriteRequestDto) {
        return ResponseEntity.ok(boardService.boardSave(boardWriteRequestDto));
    }
}
