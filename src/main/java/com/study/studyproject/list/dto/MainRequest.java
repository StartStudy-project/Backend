package com.study.studyproject.list.dto;

import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Recruit;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
