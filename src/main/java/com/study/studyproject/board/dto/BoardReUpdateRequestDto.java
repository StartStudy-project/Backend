package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Recruit;
import lombok.Builder;
import lombok.Data;

@Data
public class BoardReUpdateRequestDto {
    //모집구분
    Long boardId;
    Recruit recruit;
    String content;
    Category category;
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
