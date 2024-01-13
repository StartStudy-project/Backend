package com.study.studyproject.reply.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ReplyResponseDto {


    int getTotal;
    List<ReplyInfoDto> replies;

    @Builder
    public ReplyResponseDto(int getTotal, List<ReplyInfoDto> replies) {
        this.getTotal = getTotal;
        this.replies = replies;
    }

    public static ReplyResponseDto ReplyResponsetoDto(int getTotal, List<ReplyInfoDto> replies) {
        return builder()
                .getTotal(getTotal)
                .replies(replies)
                .build();

    }


    }
