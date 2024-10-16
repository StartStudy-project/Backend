package com.study.studyproject.reply.service;

import com.study.studyproject.reply.dto.ReplyRequestDto;
import com.study.studyproject.reply.dto.UpdateReplyRequest;

public interface ReplyService {


    void insert(Long memberId, ReplyRequestDto replyRequestDto);

    //수정
    void updateReply(UpdateReplyRequest updateReplyRequest);

    //삭제
    void deleteReply(Long num);





}
