package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Recruit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class BoardReUpdateRequestDto {
    //모집구분
    @Schema(description = "boardId", defaultValue = "1")
    Long boardId;
    @Schema(description = "모집구분", defaultValue = "모집중")
    Recruit recruit;
    @Schema(description = "변경할 내용", defaultValue = "수정 내용")
    String content;
    @Schema(description = "카테고리", defaultValue = "CS")
    Category category;
    @Schema(description = "내용", defaultValue = "제목")
    String title;


    @Builder
    public BoardReUpdateRequestDto(Long boardId, Recruit recruit, String content, Category category, String title) {
        this.boardId = boardId;
        this.recruit = recruit;
        this.content = content;
        this.category = category;
        this.title = title;
    }
}
