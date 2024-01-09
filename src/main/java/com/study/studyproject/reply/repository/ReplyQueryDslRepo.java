//package com.study.studyproject.reply.repository;
//
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.study.studyproject.entity.QReply;
//import com.study.studyproject.entity.Reply;
//import jakarta.persistence.EntityManager;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import static com.study.studyproject.entity.QReply.reply;
//
//@Repository
//public class ReplyQueryDslRepo {
//
//
//    private final JPAQueryFactory queryFactory;
//
//    public ReplyQueryDslRepo(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//
//    public List<Reply> findRepleies(Long boardId) {
//        queryFactory.select(reply)
//                .from(reply)
//                .where(reply.board.id.eq(boardId))
//                .orderBy(reply.ref.asc(),reply.createdDate.asc());
//
//
//
//    }
//
//
//
//}
