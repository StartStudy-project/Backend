package com.study.studyproject.reply.repository;

import com.study.studyproject.entity.Reply;

import java.util.List;

public interface ReplyRepositoryCustom {
    List<Reply> findByBoardReply(Long boardId);


}
