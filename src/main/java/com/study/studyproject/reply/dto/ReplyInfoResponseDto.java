package com.study.studyproject.reply.dto;

import com.study.studyproject.domain.Reply;
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
    private String nickname;
    private String content;
    private LocalDateTime updateTime;
    private List<ReplyInfoResponseDto> children = new ArrayList<>();

    public ReplyInfoResponseDto(Reply reply, String content) {
        this.replyId = reply.getId();
        this.updateTime = reply.getLastModifiedDate();
        this.nickname = reply.getNickname();
        this.parentId = (reply.getParent() != null) ? reply.getParent().getId() : null;
        this.content = content;
    }



    public static ReplyInfoResponseDto convertReplyToDto(Reply reply) {


        return reply.getIsDeleted() ?
                new ReplyInfoResponseDto(reply, "삭제된 댓글입니다.") :
                new ReplyInfoResponseDto(reply,reply.getContent());

    }

}
