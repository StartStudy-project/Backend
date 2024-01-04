package com.study.studyproject.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category{
    Recruiting("모집중"),
    Recruited("모집완료");
    private final String text;

    }
