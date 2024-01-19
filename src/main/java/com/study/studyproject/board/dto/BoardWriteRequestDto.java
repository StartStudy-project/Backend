package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Recruit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.boot.archive.scan.spi.ClassDescriptor;


//Member member, String title, Long viewCount, String content, String nickname, Category category
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardWriteRequestDto {

    String content;
    Category category;
    String title;
    String nickname;



    public Board toEntity(Member member) {
        return Board.builder()
                .member(member)
                .title(title)
                .content(content)
                .category(category)
                .nickname(nickname)
                .build();
    }

    @Builder
    public BoardWriteRequestDto(String content, Category category, String title, String nickname) {
        this.content = content;
        this.category = category;
        this.title = title;
        this.nickname = nickname;
    }


}
