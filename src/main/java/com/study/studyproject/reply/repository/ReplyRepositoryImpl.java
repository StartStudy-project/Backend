package com.study.studyproject.reply.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.studyproject.entity.Reply;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.study.studyproject.entity.QReply.reply;

public class ReplyRepositoryImpl implements ReplyRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    public ReplyRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Reply> findByBoardReply(Long boardId) {

        List<Reply> comments = queryFactory.selectFrom(reply)
                .leftJoin(reply.parent).fetchJoin()
                .leftJoin(reply.member).fetchJoin()
                .where(reply.board.id.eq(boardId))
                .orderBy(reply.parent.id.asc().nullsFirst(),
                        reply.createdDate.asc())
                .fetch();
        return comments;
    }


}
