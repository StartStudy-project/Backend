package com.study.studyproject.board.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.studyproject.board.dto.ListRequestDto;
import com.study.studyproject.board.dto.ListResponseDto;
import com.study.studyproject.board.dto.QListResponseDto;
import com.study.studyproject.entity.*;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.study.studyproject.entity.QBoard.*;
import static com.study.studyproject.entity.QMember.*;
import static com.study.studyproject.entity.QReply.reply;
import static org.springframework.util.StringUtils.isEmpty;

public class BoardRepositoryImpl implements  BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ListResponseDto> boardListPage(ListRequestDto condition, Pageable pageable) {

        List<ListResponseDto> content = getContent(condition, pageable);
        System.out.println("content.toString() = " + content.toString());

        JPAQuery<Board> countQuery = getTotal(condition);

        return PageableExecutionUtils.getPage(content,pageable, countQuery::fetchCount);
    }

    public List<ListResponseDto> getContent(ListRequestDto condition, Pageable pageable) {

        return queryFactory
                .select(
                        new QListResponseDto(
                                board.nickname,
                                board.id.intValue(),
                                board.recruit.stringValue(),
                                board.category.stringValue(),
                                board.content,
                                board.title,
                                board.createdDate,
                                board.viewCount.intValue(),
                                reply.count().intValue(),
                                board.count().intValue()

                        )

                )
                .from(board)
                .leftJoin(reply)
                .on(board.id.eq(reply.board.id))
                .where(
                        getType(condition.getType()), //모집여부
                        getUser(condition.getEmail()), //사용자 이메일
                        getCategory(condition.getCategory())

                )
                .orderBy(
                        getOrder(condition.getOrder()) // 순서
                )
                .groupBy(board)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    //cs
    private BooleanExpression getCategory(Category category) {
        if(isEmpty(category)){
            return null;
        }

        return category.equals(Category.전체) ? null : board.category.eq(category);
    }

    public JPAQuery<Board> getTotal(ListRequestDto condition) {
        return queryFactory
                .select(
                        board
                )
                .from(board)
                .where(
                        getType(condition.getType()), //모집여부
                        getUser(condition.getEmail()), //사용자 이메일
                        getCategory(condition.getCategory())
                );
    }


    private BooleanExpression getType(Recruit type) {


        return isEmpty(type) ? null : board.recruit.eq(type);
    }

    private BooleanExpression getUser(String userEmail) {
        return isEmpty(userEmail) ? null : member.email.eq(userEmail);
    }

    private OrderSpecifier<?> getOrder(int num) {
        if (num == 0) {
            System.out.println("0");
            return board.createdDate.desc();
        } else {
            System.out.println("1");
             return board.viewCount.desc();
        }
    }


}