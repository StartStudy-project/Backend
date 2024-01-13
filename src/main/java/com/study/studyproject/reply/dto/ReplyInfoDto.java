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
public class ReplyInfoDto {
    Long replyId;
    boolean isMyReply;
    String replyer;
    String content;
    LocalDateTime updateTime;
    private List<ReplyInfoDto> children = new ArrayList<>();

    public ReplyInfoDto(Reply reply, boolean isMyReply) {
        this.replyId = reply.getId();
        this.isMyReply = isMyReply;
        this.replyer = reply.getMember().getNickname();
        this.content = reply.getContent();
        this.updateTime = reply.getLastModifiedDate();
    }


    public static ReplyInfoDto convertReplyToDto(Reply reply, Long memberId) {
        boolean isMyReply = false;
        if (memberId == reply.getMember().getId()) {
            isMyReply = true;
        }
        return new ReplyInfoDto(reply, isMyReply);

    }
}
