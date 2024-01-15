package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Board;
import com.study.studyproject.reply.dto.ReplyResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardOneResponseDto {
    @Schema(description = "제목", defaultValue = "제목제목")
    String title;

    @Schema(description = "아이디", defaultValue = "jacom!!!")
    String userId;

    @Schema(description = "작성시간", defaultValue = "2023-10-05T12:34:56")
    LocalDateTime updateTime;

    @Schema(description = "게시글 내용", defaultValue = "내요내용")
    String content;

    @Schema(description = "조회수", defaultValue = "3")
    int viewCnt;

    @Schema(description = "댓글")
    ReplyResponseDto replyResponseDto;

    @Builder
    public BoardOneResponseDto(String title, String userId, LocalDateTime updateTime, String content, int viewCnt, ReplyResponseDto replyResponseDto) {
        this.title = title;
        this.userId = userId;
        this.updateTime = updateTime;
        this.content = content;
        this.viewCnt = viewCnt;
        this.replyResponseDto = replyResponseDto;
    }



    public static BoardOneResponseDto of(Board board, ReplyResponseDto replies) {
        return BoardOneResponseDto.builder()
                .updateTime(board.getLastModifiedDate())
                .title(board.getTitle())
                .content(board.getContent())
                .viewCnt(Math.toIntExact(board.getViewCount()))
                .userId(board.getNickname())
                .replyResponseDto(replies)
                .build();
    }

}
