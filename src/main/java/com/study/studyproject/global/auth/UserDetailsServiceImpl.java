package com.study.studyproject.global.auth;

import com.study.studyproject.domain.Member;
import com.study.studyproject.domain.Role;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.study.studyproject.global.exception.ex.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = null;
        try {
            member = memberRepository.findByEmail(email).orElseThrow(()->new NotFoundException(NOT_FOUND_MEMBER));
        } catch (NotFoundException e) {
            return new UserDetailsImpl(null, Role.ROLE_GUEST, 0L);
        };

        return new UserDetailsImpl(member,member.getRole(),member.getId());
    }
}
