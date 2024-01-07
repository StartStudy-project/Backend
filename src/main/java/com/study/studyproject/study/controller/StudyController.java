package com.study.studyproject.study.controller;

import com.study.studyproject.study.service.StudyService;
import com.study.studyproject.study.service.StudyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudyController {

    private  final StudyServiceImpl studyService;

    @GetMapping("/main/${type}")
    public String studyMain(
            @PathVariable(name = "type", required = false) String type,
            @RequestParam(defaultValue = "seq", required = false) String order,
            @RequestParam(defaultValue="0", required =false) int page

    ) {
        studyService.selectBoardList(type,order,page);
        return "";

    }
}
