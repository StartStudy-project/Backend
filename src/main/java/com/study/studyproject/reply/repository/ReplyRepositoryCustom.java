package com.study.studyproject.reply.repository;

import com.study.studyproject.entity.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepositoryCustom {
    List<Reply> findByBoardReply(Long boardId);


    List<Reply> findByBoardReplies(Long boardId);

    Optional<Reply> findCommentByIdWithParent(Long id);

    Long findBoardReplyCnt(Long id);


}
