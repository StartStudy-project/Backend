package com.study.studyproject.member.dto;

import com.study.studyproject.board.dto.BoardOneResponseDto;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class UserInfoResponseDto {


    @Schema(description = "순서", defaultValue = "1")
    int seq;

    @Schema(description = "사용자 이름", defaultValue = "김미")
    String username;

    @Schema(description = "사용자 닉네임", defaultValue = "jac")
    String nickname;

    @Schema(description = "사용자 이메일", defaultValue = "jac@naver.com")
    String email;

    @Schema(description = "사용자 역할", defaultValue = "ROLE_USER")
    String role;

    @Builder
    public UserInfoResponseDto(int seq, String username, String nickname, String email, String role) {
        this.seq = seq;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }



    public static UserInfoResponseDto of(Member member) {
        return UserInfoResponseDto.builder()
                .seq(member.getId().intValue())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .role(member.getRole().name())
                .build();
    }


}
