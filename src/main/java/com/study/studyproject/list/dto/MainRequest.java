package com.study.studyproject.list.dto;

import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Recruit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@NoArgsConstructor
public class MainRequest {

    @Nullable
    @Schema(description = "사용자 이름", defaultValue = "CS")
    Category category;
    
    @Schema(description = "조회", defaultValue = "0")
    int order; // 0 -> 최신순

    @Nullable
    @Schema(description = "타이틀명", defaultValue = "제목")
    String title;

    @Builder
    public MainRequest(@Nullable Category category, int order, String title) {
        this.category = category;
        this.order = order;
        this.title = title;
    }
}
