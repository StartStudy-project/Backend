package com.study.studyproject.board.dto;

import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Recruit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.boot.archive.scan.spi.ClassDescriptor;


//Member member, String title, Long viewCount, String content, String nickname, Category category
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardWriteRequestDto {


    @Schema(description = "변경할 내용", defaultValue = "수정 내용")
    @NotBlank(message = "내용을 입력해주세요")
    String content;

    @Schema(description = "카테고리", defaultValue = "CS")
    @NotNull(message = "카테고리를 입력해주세요")
    Category category;

    @Schema(description = "제목", defaultValue = "제목")
    @NotBlank(message = "제목을 입력해주세요")
    String title;

    @Schema(description = "김유역", defaultValue = "닉네임")
    @NotBlank(message = "닉네임 입력해주세요")
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
