package com.study.studyproject.reply.exception;

import com.study.studyproject.global.exception.ex.NotFoundException;

public class BoardNotFoundException extends NotFoundException {

    private static final String MESSAGE = "게시글이 없습니다.";

    public BoardNotFoundException() {
        super(MESSAGE);
    }
}
