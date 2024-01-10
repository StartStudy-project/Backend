package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Recruit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class MainRequest {

    Recruit type;
    Category category;
    int order; // 0 -> 최신순

    @Builder
    public MainRequest(Recruit type, Category category, int order) {
        this.type = type;
        this.category = category;
        this.order = order;
    }
}
