package com.study.studyproject.main.controller;

import com.study.studyproject.main.dto.MainReqestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController
{

    //메인 파트
    @GetMapping("/")
    public String getMainlist(MainReqestDto mainReqestDto , @RequestParam(defaultValue = "0", required = false) int page,
                              @PageableDefault(size = 10) Pageable pageable) {
        boardService.BoardService(mainReqestDto,page,pageable);
        return "";

    }
}
