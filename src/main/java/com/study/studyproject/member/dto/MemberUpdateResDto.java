package com.study.studyproject.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateResDto {
    @Schema(description = "사용자 이름", defaultValue = "김하하")
    String username;

    @Schema(description = "사용자 닉네임", defaultValue = "sdd")
    String nickname;
}
