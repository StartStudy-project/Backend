package com.study.studyproject.member.service;

import com.study.studyproject.entity.Member;
import com.study.studyproject.member.dto.MemberListRequestDto;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final MemberRepository memberRepository;

    @Override
    public Page<UserInfoResponseDto> userInfoAll(String username, Pageable pageable) {
        List<UserInfoResponseDto> contents = memberRepository.getContent(username,pageable).stream().map(i -> UserInfoResponseDto.of(i)).collect(Collectors.toList());
        return memberRepository.adminUserBoardList(username,contents, pageable);


    }

}
