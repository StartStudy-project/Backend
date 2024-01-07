package com.study.studyproject.board.controller;

import com.study.studyproject.board.dto.BoardReUpdateRequestDto;
import com.study.studyproject.board.dto.BoardWriteRequestDto;
import com.study.studyproject.board.dto.MainReqestDto;
import com.study.studyproject.board.service.BoardService;
import com.study.studyproject.global.GlobalResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    BoardService boardService;
    //글쓰기 수정
    @PatchMapping("/writing")
    public String updateWriting(BoardReUpdateRequestDto boardReUpdateRequestDto) {

        boardService.updateWrite(boardReUpdateRequestDto);
        return "";
    }

    //글쓰기 작성
    @PostMapping("/writing")
    public ResponseEntity<GlobalResultDto> writing(@CookieValue(value = "Refresh_Token") String token, BoardWriteRequestDto boardWriteRequestDto) {
        return ResponseEntity.ok(boardService.boardSave(boardWriteRequestDto, token));
    }


    @GetMapping("/")
    public String getMainlist(MainReqestDto mainReqestDto ,@RequestParam(defaultValue = "0", required = false) int page,
    @PageableDefault(size = 10) Pageable pageable) {
        boardService.BoardService(mainReqestDto,page,pageable);
        return "";

    }
}
