package com.study.studyproject.global.auth;

import com.study.studyproject.domain.Member;
import com.study.studyproject.domain.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class UserDetailsImpl implements UserDetails {


    private Role authority;
    private Long memberId;
    private  Member member;


    public UserDetailsImpl(Member member, Role authority, Long memberId) {
        this.member = member;
        this.authority = authority;
        this.memberId = memberId;
    }

    public UserDetailsImpl guest() {
        return new UserDetailsImpl(null,Role.ROLE_GUEST, 0L);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() { //권한주는
                return authority.name();
            }

        })
        ;

        return collection;
    }

    @Override
    public String getPassword() {
        return member != null ? member.getPassword():null;
    }

    @Override
    public String getUsername() {
        return member != null ? member.getUsername():null;
    }

    public String getNickname() {
        return member != null ? member.getNickname():null;
    }

    public boolean isGuest(){
        return member == null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
