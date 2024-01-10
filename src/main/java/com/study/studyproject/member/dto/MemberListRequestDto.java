package com.study.studyproject.member.dto;

import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Recruit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberListRequestDto  {

    @Schema(description = "모집구분", defaultValue = "모집중")
    Recruit type;   // 모집중

    @Schema(description = "카테고리", defaultValue = "CS")
    Category category;   // cs

    @Schema(description = "순서", defaultValue = "최신순")
    int order ; // 0 -> 최신순


    String  email;
    
    
    @Builder
    public MemberListRequestDto(Recruit type, Category category, int order) {
        this.type = type;
        this.category = category;
        this.order = order;
    }
}
