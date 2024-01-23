package com.study.studyproject.reply.dto;

import com.study.studyproject.entity.Reply;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ReplyInfoResponseDto {
    private Long replyId;
    private Long parentId;
    private MemberReplyRequestDto memberRequestDto;
    private String content;
    private LocalDateTime updateTime;
    private List<ReplyInfoResponseDto> children = new ArrayList<>();
    private boolean myComment;


    public ReplyInfoResponseDto(Reply reply, String content, MemberReplyRequestDto userInfoResponseDto) {
        this.replyId = reply.getId();
        this.updateTime = reply.getLastModifiedDate();
        this.parentId = (reply.getParent() != null) ? reply.getParent().getId() : null;
        this.content = content;
        this.memberRequestDto = userInfoResponseDto;
    }



    public static ReplyInfoResponseDto convertReplyToDto(Reply reply) {
        return reply.getIsDeleted() ?
                new ReplyInfoResponseDto(reply, "삭제된 댓글입니다.", null) :
                new ReplyInfoResponseDto(reply, reply.getContent(), new MemberReplyRequestDto(reply.getMember()));

    }

}
