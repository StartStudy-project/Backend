package com.study.studyproject.reply.dto;

import com.study.studyproject.entity.Reply;
import lombok.Data;
import lombok.Getter;

@Data
public class ReplyRequestDto {
    private Long boardId;
    private Long parentId;
    private String content;



}
