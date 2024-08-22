package com.study.studyproject.list.dto;

import com.study.studyproject.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainRequest {

    Category category;

    int order; // 0 -> 최신순

    @Builder
    public MainRequest( Category category, Integer order) {
        this.category = category;
        this.order = order;
    }
}
