package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Recruit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardChangeRecruitRequestDto {

    @Schema(description = "게시글 num", defaultValue = "1")
    @NotNull(message = "게시글 번호를 입력해주세요")
    Long boardId;

    @Schema(description = "모집 구분", defaultValue = "모집중")
    @NotNull(message = "모집구분을 입력해주세요")
    Recruit recruit;

    @Builder
    public BoardChangeRecruitRequestDto(Long boardId, Recruit recruit) {
        this.boardId = boardId;
        this.recruit = recruit;
    }
}
