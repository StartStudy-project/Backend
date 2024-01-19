package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Recruit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardChangeRecruitRequestDto {

    @Schema(description = "게시글 num", defaultValue = "1")
    Long boardId;

    @Schema(description = "모집 구분", defaultValue = "모집중")
    Recruit recruit;

}
