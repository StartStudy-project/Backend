package com.study.studyproject.reply.dto;

import com.study.studyproject.entity.Reply;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyRequestDto {
    private Long boardId;
    private Long parentId;
    private String content;



}
