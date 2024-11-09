package com.study.studyproject.global.oauth;

import com.study.studyproject.global.oauth.provider.GoogleOAuth2UserInfo;
import com.study.studyproject.global.oauth.provider.KakaoOAuth2UserInfo;
import com.study.studyproject.global.oauth.provider.NaverOAuth2UserInfo;
import com.study.studyproject.global.oauth.provider.OAuth2UserInfo;
import com.study.studyproject.login.domain.Role;
import com.study.studyproject.member.domain.Member;
import com.study.studyproject.member.domain.SocialType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

import static com.study.studyproject.member.domain.SocialType.KAKAO;
import static com.study.studyproject.member.domain.SocialType.NAVER;

@Getter
public class OAuthAttributes {
    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName, Map<String, Object> attributes) {

        if (socialType == NAVER) {
            return ofNaver(userNameAttributeName, attributes);
        }
        if (socialType == KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }


    static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    public Member toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        return Member.builder().password("" + UUID.randomUUID())
                .email(oauth2UserInfo.getEmail())
                .socialId(oauth2UserInfo.getId())
                .username(oauth2UserInfo.getName())
                .nickname(oauth2UserInfo.getNickname())
                .socialType(socialType)
                .role(Role.ROLE_USER).build();
    }
}
