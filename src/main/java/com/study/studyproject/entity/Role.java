package com.study.studyproject.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");
    private final String text;

    public static boolean containsStockType(Role type) {
        return List.of(USER, ADMIN).contains(type);
    }
}
