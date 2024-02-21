package com.study.studyproject.reply.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateReplyRequest {

    @Schema(description = "댓글 num", defaultValue = "1")
    @NotNull(message = "댓글 번호를 입력해주세요")
    Long replyId;

    @Schema(description = "댓글 내용", defaultValue = "수정한 내용")
    @NotBlank(message = "댓글 내용을 입렬해주새요")
    String content;

    @Builder
    public UpdateReplyRequest(Long replyId, String content) {
        this.replyId = replyId;
        this.content = content;
    }
}
