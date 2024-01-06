package com.study.studyproject.global.auth;

import com.study.studyproject.entity.Member;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import com.study.studyproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// userDetailsImple에 account를 넣어주는 서비스입니다.
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("사용자를 찾지 못했습니다.")
        );

        UserDetailsImpl userDetails = new UserDetailsImpl(member);

        return userDetails;
    }
}
