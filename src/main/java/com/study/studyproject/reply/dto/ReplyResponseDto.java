package com.study.studyproject.reply.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ReplyResponseDto {


    long getTotal;
    List<ReplyInfoResponseDto> replies;

    @Builder
    public ReplyResponseDto(long getTotal, List<ReplyInfoResponseDto> replies) {
        this.getTotal = getTotal;
        this.replies = replies;
    }

    public static ReplyResponseDto ReplyResponsetoDto(long getTotal, List<ReplyInfoResponseDto> replies) {
        return builder()
                .getTotal(getTotal)
                .replies(replies)
                .build();

    }


    }
