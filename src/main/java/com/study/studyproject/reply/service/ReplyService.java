package com.study.studyproject.reply.service;

import com.study.studyproject.entity.Member;
import com.study.studyproject.reply.dto.ReplyRequestDto;
import com.study.studyproject.reply.dto.ReplyResponseDto;
import com.study.studyproject.reply.dto.UpdateReplyRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface ReplyService {


    void insert(Member member, ReplyRequestDto replyRequestDto);

    //수정
    void updateReply(UpdateReplyRequest updateReplyRequest);

    //삭제
    void deleteReply(Long num);





}
