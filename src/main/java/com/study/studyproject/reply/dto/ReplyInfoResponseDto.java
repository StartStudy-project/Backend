package com.study.studyproject.reply.dto;

import com.study.studyproject.entity.Reply;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyInfoResponseDto {
    private Long replyId;
    private MemberReplyRequestDto memberRequestDto;
    private String content;
    private LocalDateTime updateTime;
    private List<ReplyInfoResponseDto> children = new ArrayList<>();

    public ReplyInfoResponseDto(Long id, LocalDateTime lastModifiedDate, String content, MemberReplyRequestDto userInfoResponseDto) {
        this.replyId = id;
        this.updateTime = lastModifiedDate;
        this.content = content;
        this.memberRequestDto = userInfoResponseDto;
    }



    public static ReplyInfoResponseDto convertReplyToDto(Reply reply) {
        return reply.getIsDeleted() ?
                new ReplyInfoResponseDto(reply.getId(),reply.getLastModifiedDate(), "삭제된 댓글입니다.", null) :
                new ReplyInfoResponseDto(reply.getId(),reply.getLastModifiedDate(), reply.getContent(), new MemberReplyRequestDto(reply.getMember()));

    }

}
