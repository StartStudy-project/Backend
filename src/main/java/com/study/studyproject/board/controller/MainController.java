package com.study.studyproject.board.controller;

import com.study.studyproject.board.dto.ListRequestDto;
import com.study.studyproject.board.dto.ListResponseDto;
import com.study.studyproject.board.dto.MainRequest;
import com.study.studyproject.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {


    private final BoardService boardService;

    @GetMapping("/")
    public ResponseEntity<Page<ListResponseDto>> mainList(@RequestBody MainRequest mainRequestDto,
                                                          @PageableDefault(size = 10) Pageable pageable) {

        System.out.println("mainRequestDto = " + mainRequestDto);
        Page<ListResponseDto> list = boardService.list(mainRequestDto, pageable);


        return ResponseEntity.ok(list);
    }

}
