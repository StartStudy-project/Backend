package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Recruit;
import com.study.studyproject.reply.dto.ReplyResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardOneResponseDto {

    @Schema(description = "게시글 내용", defaultValue = "내요내용")
    boolean isMyBoard;

    Recruit recruit;

    @Schema(description = "제목", defaultValue = "제목제목")
    String title;

    @Schema(description = "아이디", defaultValue = "jacom!!!")
    String userId;

    @Schema(description = "수정 시간", defaultValue = "2023-10-05T12:34:56")
    LocalDateTime updateTime;

    @Schema(description = "작성 시간", defaultValue = "2023-10-05T12:34:56")
    LocalDateTime createTime;

    @Schema(description = "게시글 내용", defaultValue = "내요내용")
    String content;

    Category category;
    @Schema(description = "조회수", defaultValue = "3")
    int viewCnt;

    @Schema(description = "댓글")
    ReplyResponseDto replyResponseDto;

    @Builder
    public BoardOneResponseDto(Category category,String title, String userId, LocalDateTime updateTime, String content, boolean isMyBoard, int viewCnt, ReplyResponseDto replyResponseDto,LocalDateTime createTime,Recruit recruit) {
        this.recruit = recruit;
        this.title = title;
        this.userId = userId;
        this.updateTime = updateTime;
        this.content = content;
        this.category = category;
        this.isMyBoard = isMyBoard;
        this.viewCnt = viewCnt;
        this.replyResponseDto = replyResponseDto;
        this.createTime = createTime;
    }






    public static BoardOneResponseDto of(Board board, ReplyResponseDto replies, Long currentMemberId) {

        boolean myBoard = false;
        if (board.getMember().getId().equals(currentMemberId)) {
            myBoard = true;
        }

        return BoardOneResponseDto.builder()
                .isMyBoard(myBoard)
                .updateTime(board.getLastModifiedDate())
                .createTime(board.getCreatedDate())
                .title(board.getTitle())
                .recruit(board.getRecruit())
                .content(board.getContent())
                .viewCnt(Math.toIntExact(board.getViewCount()))
                .userId(board.getNickname())
                .category(board.getCategory())
                .replyResponseDto(replies)
                .build();
    }

}
