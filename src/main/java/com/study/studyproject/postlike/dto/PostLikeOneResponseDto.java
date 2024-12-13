package com.study.studyproject.postlike.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.studyproject.board.dto.BoardOneResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostLikeOneResponseDto {


    @Schema(description = "관심글", defaultValue = "관심중")
    String postLike;

    @Schema(description = "관심글ID", defaultValue = "2")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Long postLikeId;


    @Builder
    public PostLikeOneResponseDto(String postLike, Long postLikeId) {
        this.postLike = postLike;
        this.postLikeId = postLikeId;
    }

    public static PostLikeOneResponseDto of(String postLike, Long postLikeId) {
        return PostLikeOneResponseDto.builder()
                .postLike(postLike)
                .postLikeId(postLikeId)
                .build();
    }
}
