package com.study.studyproject.list.dto;

import com.querydsl.core.Tuple;
import com.querydsl.core.annotations.QueryProjection;
import com.study.studyproject.entity.Recruit;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ListResponseDto {

    String nickname;
    int boardId;
    String recurit; //모집중
    String type; // cs
    String content;
    String title;

    @DateTimeFormat(pattern = "yyyy-mm-dd'T'HH:mm:ss")
    LocalDateTime time;
    int hitCnt; // 조회수
    int replyCnt; //

    @QueryProjection
    public ListResponseDto(String nickname, int boardId, String recurit, String type, String content, String title, LocalDateTime time, int hitCnt, int replyCnt ) {
        this.nickname = nickname;
        this.boardId = boardId;
        this.recurit = recurit;
        this.type = type;
        this.content = content;
        this.title = title;
        this.time = time;
        this.hitCnt = hitCnt;
        this.replyCnt = replyCnt;
    }
}
