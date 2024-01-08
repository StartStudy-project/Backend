package com.study.studyproject.reply.service;

import com.study.studyproject.reply.dto.ReplyResponseDto;

public interface ReplyService {

    //조회
    ReplyResponseDto replyResponseDto() ;

    //수정
    void updateReply(Long num);

    //삭제
    void deleteReply(Long num);


    //댓글 갯수 리턴 메소드
    int getReplyCnt(Long num);




}
