package com.study.studyproject.member.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.studyproject.entity.Member;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.study.studyproject.entity.QMember.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<UserInfoResponseDto> adminUserBoardList(List<UserInfoResponseDto> userInfoResponseDtos , Pageable pageable) {
        JPAQuery<Member> countQuery = getTotal();
        return PageableExecutionUtils.getPage(userInfoResponseDtos,pageable, countQuery::fetchCount);
    }

    @Override
    public List<Member> getContent(Pageable pageable) {
        return queryFactory.
                selectFrom(member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    public JPAQuery<Member> getTotal() {
        return queryFactory.
                selectFrom(member);
    }
}
