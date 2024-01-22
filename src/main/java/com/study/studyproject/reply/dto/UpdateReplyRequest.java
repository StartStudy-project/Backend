package com.study.studyproject.reply.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateReplyRequest {

    @Schema(description = "댓글 num", defaultValue = "1")
    Long replyId;

    @Schema(description = "댓글 내용", defaultValue = "수정한 내용")
    String content;
}
