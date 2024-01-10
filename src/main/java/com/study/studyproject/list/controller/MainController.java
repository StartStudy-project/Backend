package com.study.studyproject.list.controller;

import com.study.studyproject.list.dto.ListResponseDto;
import com.study.studyproject.board.dto.MainRequest;
import com.study.studyproject.list.service.ListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "메인 기능 구현")
public class MainController {
//

    private final ListService listService;

    @GetMapping("/")
    public ResponseEntity<Page<ListResponseDto>> mainList(
            @RequestParam(required = false) String content,
            @RequestBody MainRequest mainRequestDto,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<ListResponseDto> list = listService.list(content, mainRequestDto, pageable);


        return ResponseEntity.ok(list);
    }

}
