package com.study.studyproject.global.oauth.provider;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId(); // 소셜 식별 : 구글 : sub  ,카카오 id, 네이버 id
    public abstract  String getEmail();
    public abstract  String getName();
    public abstract  String getNickname();
}
