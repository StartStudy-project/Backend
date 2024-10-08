package com.study.studyproject.board.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.studyproject.domain.Board;
import com.study.studyproject.domain.Category;
import com.study.studyproject.domain.Recruit;
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

    Recruit recruit;
    @Schema(description = "현재 닉네임", defaultValue = "jacom!!")
    String currentNickname;

    @Schema(description = "제목", defaultValue = "제목제목")
    String title;

    @Schema(description = "게시글 작성자", defaultValue = "jacom!!!")
    String boardWriteNickname;


    @Schema(description = "수정 시간", defaultValue = "2023-10-05T12:34:56")
    LocalDateTime updateTime;

    @Schema(description = "작성 시간", defaultValue = "2023-10-05T12:34:56")
    LocalDateTime createTime;

    @Schema(description = "게시글 내용", defaultValue = "내요내용")
    String content;

    Category category;
    
    @Schema(description = "조회수", defaultValue = "3")
    int viewCnt;

    @Schema(description = "관심글", defaultValue = "관심중")
    String postLike;

    @Schema(description = "관심글ID", defaultValue = "2")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Long postLikeId;


    @Schema(description = "댓글")
    ReplyResponseDto replyResponseDto;

    @Builder
    public BoardOneResponseDto(Recruit recruit, String currentNickname, String title, String boardWriteNickname, LocalDateTime updateTime, LocalDateTime createTime, String content, Category category, int viewCnt, String postLike, Long postLikeId, ReplyResponseDto replyResponseDto) {
        this.recruit = recruit;
        this.currentNickname = currentNickname;
        this.title = title;
        this.boardWriteNickname = boardWriteNickname;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.content = content;
        this.category = category;
        this.viewCnt = viewCnt;
        this.postLike = postLike;
        this.postLikeId = postLikeId;
        this.replyResponseDto = replyResponseDto;
    }








    public static BoardOneResponseDto of(Long postLikeId, String postLike, Board board, ReplyResponseDto replies, String nickname) {

        String nickname1 = board.getMember().getNickname();
        System.out.println("nickname1 = " + nickname1);
        return BoardOneResponseDto.builder()
                .updateTime(board.getLastModifiedDate())
                .createTime(board.getCreatedDate())
                .title(board.getTitle())
                .recruit(board.getRecruit())
                .content(board.getContent())
                .postLike(postLike)
                .postLikeId(postLikeId)
                .viewCnt(Math.toIntExact(board.getViewCount()))
                .boardWriteNickname(nickname1)
                .currentNickname(nickname)
                .category(board.getCategory())
                .replyResponseDto(replies)
                .build();
    }
    
}

