package com.study.studyproject.global.oauth;

import com.study.studyproject.global.auth.UserDetailsImpl;
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
import static com.study.studyproject.member.domain.SocialType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("----- OAuth2.0 로그인 ----------");
        System.out.println("userRequest = " + userRequest.getClientRegistration()); // registrationId로 어떤 oAuth로그인 했는지 확인 가능
        System.out.println("user " + userRequest.getAccessToken()); // registrationId로 어떤 oAuth로그인 했는지 확인 가능

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getClientId();
        System.out.println("registrationId = " + registrationId);
        SocialType socialType = getSocialType(registrationId);
        System.out.println("socialType = " + socialType);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값

        System.out.println("userNameAttributeName = " + userNameAttributeName);
        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)
        System.out.println("attributes = " + attributes);
        log.info("attributes :: " + attributes);


        OAuthAttributes extractAttributes = of(socialType, userNameAttributeName, attributes);
        Member createdUser = getUser(extractAttributes, socialType); // getUser() 메소드로 User 객체 생성 후 반환

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
        if (NAVER.equals(registrationId)) {
            return NAVER;
        }
        if (KAKAO.equals(registrationId)) {
            return KAKAO;
        }
        return KAKAO;
    }
}
