package com.study.studyproject.reply.dto;

import com.study.studyproject.entity.Reply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyRequestDto {
    @Schema(description = "게시글 num", defaultValue = "1")
    private Long boardId;

    @Schema(description = "부모 댓글 num ", defaultValue = "1")
    private Long parentId;
    
    @Schema(description = "댓글 내용", defaultValue = "댓글 내용입니다")
    private String content;



}
