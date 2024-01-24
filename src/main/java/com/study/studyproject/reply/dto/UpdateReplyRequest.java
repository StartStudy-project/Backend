package com.study.studyproject.reply.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateReplyRequest {

    @Schema(description = "댓글 num", defaultValue = "1")
    Long replyId;

    @Schema(description = "댓글 내용", defaultValue = "수정한 내용")
    String content;

    @Builder
    public UpdateReplyRequest(Long replyId, String content) {
        this.replyId = replyId;
        this.content = content;
    }
}
