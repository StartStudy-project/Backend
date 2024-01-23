package com.study.studyproject.reply.dto;

import com.study.studyproject.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MemberReplyRequestDto {

    @Schema(description = "사용자 Id", defaultValue = "1")
    Long memberId;

    @Schema(description = "사용자 닉네임", defaultValue = "jac")
    String nickname;

    public MemberReplyRequestDto(Member member) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
    }
}
