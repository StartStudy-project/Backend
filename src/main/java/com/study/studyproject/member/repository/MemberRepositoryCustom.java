package com.study.studyproject.member.repository;

import com.study.studyproject.member.domain.Member;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {

    Page<UserInfoResponseDto> adminUserBoardList(String username, List<UserInfoResponseDto> userInfoResponseDtos, Pageable pageable);
    List<Member> getContent(String username, Pageable pageable);


}
