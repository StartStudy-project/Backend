package com.study.studyproject.global.oauth;

import com.study.studyproject.global.auth.UserDetailsImpl;
import com.study.studyproject.global.oauth.provider.OAuth2UserInfo;
import com.study.studyproject.member.domain.Member;
import com.study.studyproject.member.domain.SocialType;
import com.study.studyproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.study.studyproject.global.oauth.OAuthAttributes.of;
import static com.study.studyproject.global.oauth.OAuthAttributes.ofNaver;
import static com.study.studyproject.member.domain.SocialType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("----- OAuth2.0 로그인 ----------");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuthAttributes extractAttributes = of(socialType, userNameAttributeName, attributes);
        Member createdUser = getUser(extractAttributes, socialType);

        return new UserDetailsImpl(createdUser,
                createdUser.getId(),createdUser.getRole(),createdUser.getEmail());
    }

    private Member getUser(OAuthAttributes attributes, SocialType socialType) {
        Member findMember = memberRepository.findBySocialTypeAndSocialId(socialType,
                attributes.getOauth2UserInfo().getId()).orElse(null);
        if (findMember == null) {
            return saveMember(attributes, socialType);
        }
        return findMember;
    }

    private Member saveMember(OAuthAttributes attributes, SocialType socialType) {
        Member createMember = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return memberRepository.save(createMember);
    }


    private SocialType getSocialType(String registrationId) {
        if ("NAVER".equals(registrationId)) {
            return NAVER;
        }
        if ("KAKAO".equals(registrationId)) {
            return KAKAO;
        }

        return null;
    }
}
