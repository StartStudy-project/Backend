package com.study.studyproject.reply.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ReplyResponseDto {


    long getTotal;
    List<ReplyInfoResponseDto> replies;

    @Builder
    public ReplyResponseDto(long getTotal, List<ReplyInfoResponseDto> replies) {
        this.getTotal = getTotal;
        this.replies = replies;
    }

    public static ReplyResponseDto ReplyResponseToDto(long getTotal, List<ReplyInfoResponseDto> replies) {
        return builder()
                .getTotal(getTotal)
                .replies(replies)
                .build();

    }


    }
