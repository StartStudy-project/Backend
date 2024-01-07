package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Recruit;
import lombok.Data;

@Data
public class BoardReUpdateRequestDto {

    //모집구분
    Recruit recruit;
    String content;
    Category category;
    String title;

}
