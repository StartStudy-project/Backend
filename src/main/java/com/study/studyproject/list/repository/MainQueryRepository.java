package com.study.studyproject.list.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.studyproject.list.dto.ListResponseDto;
import com.study.studyproject.list.dto.MainRequest;
import com.study.studyproject.entity.*;
import com.study.studyproject.list.dto.QListResponseDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.study.studyproject.entity.QBoard.*;
import static com.study.studyproject.entity.QReply.reply;
import static org.springframework.util.StringUtils.isEmpty;

@Repository
public class MainQueryRepository  {

    private final JPAQueryFactory queryFactory;

    public MainQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<ListResponseDto> boardListPage(MainRequest condition, Pageable pageable) {

        List<ListResponseDto> content = getContent(condition, pageable);
        JPAQuery<Board> countQuery = getTotal(condition);

        return PageableExecutionUtils.getPage(content,pageable, countQuery::fetchCount);
    }

    public List<ListResponseDto> getContent(MainRequest condition, Pageable pageable) {

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
                                reply.count().intValue()
                        )

                )
                .from(board)
                .leftJoin(reply).fetchJoin()
                .on(board.id.eq(reply.board.id))
                .where(
                        board.recruit.eq(Recruit.모집중),
                        getCategory(condition.getCategory()),
                        getFindContent(condition.getTitle())
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

    public JPAQuery<Board> getTotal(MainRequest condition) {
        return queryFactory
                .select(
                        board
                )
                .from(board)
                .where(
                        board.recruit.eq(Recruit.모집중),
                        getCategory(condition.getCategory()),
                        getFindContent(condition.getTitle())
                );
    }



    private BooleanExpression getFindContent(String findContent) {
        return isEmpty(findContent) ? null : board.title.startsWith(findContent);
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
