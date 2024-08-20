package com.study.studyproject.entity;

import java.util.Optional;
import java.util.function.Supplier;

public enum PostLikeState  {
    LIKE("관심"), LIKING("관심 완료");

    private String name;

    PostLikeState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
