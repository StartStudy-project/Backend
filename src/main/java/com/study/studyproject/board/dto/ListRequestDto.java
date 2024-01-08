package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Recruit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public abstract class ListRequestDto {
    Recruit type;   // 모집중
    Category category;   // cs
    int order ; // 0 -> 최신순
    Integer  userId ;

//
//    public ListRequestDto(Recruit type, Category category, int order) {
//        this.type = type;
//        this.category = category;
//        this.order = order;
//    }
}
