package com.study.studyproject.member.dto;

import com.study.studyproject.board.dto.ListRequestDto;
import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Recruit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MemberListRequestDto extends ListRequestDto {

    @Schema(description = "모집구분", defaultValue = "모집중")
    Recruit type;   // 모집중

    @Schema(description = "카테고리", defaultValue = "CS")
    Category category;   // cs

    @Schema(description = "순서", defaultValue = "최신순")
    int order ; // 0 -> 최신순


    String  email;
}
