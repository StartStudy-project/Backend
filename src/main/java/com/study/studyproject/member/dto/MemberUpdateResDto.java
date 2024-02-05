package com.study.studyproject.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateResDto {
    @Schema(description = "사용자 이름", defaultValue = "김하하")
    @NotEmpty(message = "사용자 이름을 입력해주세요")
    String username;

    @Schema(description = "사용자 닉네임", defaultValue = "sdd")
    @NotEmpty(message = "사용자 닉네임을 입력해주세요")
    String nickname;

    public MemberUpdateResDto(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
