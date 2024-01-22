package com.study.studyproject.reply.dto;


import lombok.Data;

@Data
public class UpdateReplyRequest {

    Long replyId;
    String content;
}
