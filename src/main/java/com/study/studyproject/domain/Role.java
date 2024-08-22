package com.study.studyproject.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER,
    ROLE_ADMIN
    ,ROLE_GUEST;

    public static boolean containsLoginRoleType(Role type) {
        return List.of(ROLE_USER, ROLE_ADMIN).contains(type);
    }

}
