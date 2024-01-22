package com.study.studyproject.reply.dto;

import com.study.studyproject.entity.Reply;
import com.study.studyproject.member.dto.UserInfoResponseDto;
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
    private MemberReplyRequestDto  memberReqestDto ;
    private String content;
    private LocalDateTime updateTime;
    private List<ReplyInfoResponseDto> children = new ArrayList<>();

    public ReplyInfoResponseDto(Long id, LocalDateTime lastModifiedDate, String content, MemberReplyRequestDto userInfoResponseDto) {
        this.replyId = id;
        this.updateTime = lastModifiedDate;
        this.content = content;
        this.memberReqestDto = userInfoResponseDto;
    }



    public static ReplyInfoResponseDto convertReplyToDto(Reply reply) {
        return reply.getIsDeleted() ?
                new ReplyInfoResponseDto(reply.getId(),reply.getLastModifiedDate(), "삭제된 댓글입니다.", null) :
                new ReplyInfoResponseDto(reply.getId(),reply.getLastModifiedDate(), reply.getContent(), new MemberReplyRequestDto(reply.getMember()));

    }

}
