package com.study.studyproject.global.exception.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    MEMBER_DUPLICATED("이미 회원가입 하였습니다.", HttpStatus.CONFLICT),
    NOT_FOUND_MEMBER("사용자를 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_PASSWORD("비밀번호가 틀립니다.", HttpStatus.CONFLICT),

    NOT_FOUND_BOARD("게시글이 없습니다.", HttpStatus.NOT_FOUND),
    UNABLE_DELETE_BOARD("게시글을 삭제 할 수 없습니다.", HttpStatus.CONFLICT),
    NOT_FOUND_REPLY("댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    TOKEN_EXPIRED( "유효 기간이 만료된 토큰입니다.",HttpStatus.FORBIDDEN),
    UNABLE_ACCESS( "접근 권한이 없습니다.",HttpStatus.FORBIDDEN),


    NOT_FOUND_PAGE("페이지가 없습니다.",HttpStatus.NOT_FOUND ),
    INTERNAL_SEVER_ERROR("서버 에러가 발생하였습니다. 관리자에게 문의해 주세요.",HttpStatus.INTERNAL_SERVER_ERROR );

    private final HttpStatus status;
    private final String message;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}