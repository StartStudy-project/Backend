package com.study.studyproject.list.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.study.studyproject.list.dto.QListResponseDto is a Querydsl Projection type for ListResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QListResponseDto extends ConstructorExpression<ListResponseDto> {

    private static final long serialVersionUID = -207978355L;

    public QListResponseDto(com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<Integer> boardId, com.querydsl.core.types.Expression<String> recurit, com.querydsl.core.types.Expression<String> type, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<java.time.LocalDateTime> time, com.querydsl.core.types.Expression<Integer> hitCnt, com.querydsl.core.types.Expression<Integer> replyCnt) {
        super(ListResponseDto.class, new Class<?>[]{String.class, int.class, String.class, String.class, String.class, String.class, java.time.LocalDateTime.class, int.class, int.class}, nickname, boardId, recurit, type, content, title, time, hitCnt, replyCnt);
    }

}

