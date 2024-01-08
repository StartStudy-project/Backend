
package com.study.studyproject.board.controller;

import com.study.studyproject.board.dto.BoardReUpdateRequestDto;
import com.study.studyproject.board.dto.BoardWriteRequestDto;
import com.study.studyproject.board.service.BoardService;
import com.study.studyproject.global.GlobalResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;


    //글쓰기 수정
    @PatchMapping("/updateWrite")
    public void updateWriting(@RequestBody BoardReUpdateRequestDto boardReUpdateRequestDto) {

        System.out.println("boardReUpdateRequestDto = " + boardReUpdateRequestDto);
        boardService.updateWrite(boardReUpdateRequestDto);
    }

    //글쓰기 작성
    @PostMapping("/writing")
    public ResponseEntity<GlobalResultDto> writing(@CookieValue(value = "Refresh_Token") String token, @RequestBody BoardWriteRequestDto boardWriteRequestDto) {
        System.out.println("boardWriteRequestDto = " + boardWriteRequestDto);
        System.out.println("token = " + token);
        GlobalResultDto body = boardService.boardSave(boardWriteRequestDto, token);
        return ResponseEntity.ok(body);
    }



}
