package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BoardOneResponseDto {
    String title;
    String userId;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    String content;
    int viewCnt;
    int hitCnt;

    @Builder
    public BoardOneResponseDto(String title, String userId, LocalDateTime createTime, LocalDateTime updateTime, String content, int viewCnt, int hitCnt) {
        this.title = title;
        this.userId = userId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.content = content;
        this.viewCnt = viewCnt;
        this.hitCnt = hitCnt;
    }

    public static BoardOneResponseDto of(Board board) {
        return BoardOneResponseDto.builder()
                .updateTime(board.getLastModifiedDate())
                .title(board.getTitle())
                .content(board.getContent())
                .hitCnt(Math.toIntExact(board.getViewCount()))
                .createTime(board.getCreatedDate())
                .userId(board.getNickname())
                .build();
    }
}
