package com.study.studyproject.global.oauth;

import com.study.studyproject.member.domain.SocialType;

import static com.study.studyproject.member.domain.SocialType.KAKAO;
import static com.study.studyproject.member.domain.SocialType.NAVER;

public class SocialTypeResolver {
    public static  SocialType getSocialType(String registrationId) {
        if ("naver".equalsIgnoreCase(registrationId)) {
            return NAVER;
        }
        if ("kakao".equalsIgnoreCase((registrationId))) {
            return KAKAO;
        }

        return null;
    }

}
