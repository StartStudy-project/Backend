package com.study.studyproject.global;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalResultDto {
    @Schema(description = "메시지", defaultValue = "~~ 완료")
    private String message;
    @Schema(description = "상태코드", defaultValue = "200")
    private int statusCode;

    public GlobalResultDto(String msg, int statusCode) {
        this.message = msg;
        this.statusCode = statusCode;
    }

}
