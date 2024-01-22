package com.study.studyproject.reply.dto;

import com.study.studyproject.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MemberReplyRequestDto {

    @Schema(description = "순서", defaultValue = "1")
    Long seq;

    @Schema(description = "사용자 닉네임", defaultValue = "jac")
    String nickname;

    @Schema(description = "사용자 역할", defaultValue = "ROLE_USER")
    String role;

    public MemberReplyRequestDto(Member member) {
        this.seq = member.getId();
        this.nickname = member.getNickname();
        this.role = member.getRole().toString();
    }
}
