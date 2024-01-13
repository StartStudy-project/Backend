package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Recruit;
import lombok.Builder;
import lombok.Data;
import org.hibernate.boot.archive.scan.spi.ClassDescriptor;


//Member member, String title, Long viewCount, String content, String nickname, Category category
@Data
public class BoardWriteRequestDto {

    //모집구분
    Recruit recruit;
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
    public BoardWriteRequestDto(Recruit recruit, String content, Category category, String title, String nickname) {
        this.recruit = recruit;
        this.content = content;
        this.category = category;
        this.title = title;
        this.nickname = nickname;
    }


}
