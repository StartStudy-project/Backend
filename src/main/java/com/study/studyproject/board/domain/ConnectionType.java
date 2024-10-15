package com.study.studyproject.board.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ConnectionType {


    OFFLINE("오프라인"), ONLINE("온라인");

    @JsonValue
    private String description;

    ConnectionType(String description) {
        this.description = description;
    }

    public String getName() {
        return description;
    }

}
