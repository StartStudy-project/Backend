package com.study.studyproject.member.service;

import com.study.studyproject.entity.Member;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.list.dto.ListResponseDto;
import com.study.studyproject.member.dto.AdminDashBoardResponseDto;
import com.study.studyproject.member.dto.MemberListRequestDto;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.member.repository.MyPageQueryRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final MemberRepository memberRepository;
    private final MyPageQueryRepository myPageQueryRepository;

    @Override
    public Page<UserInfoResponseDto> userInfoAll(String username, Pageable pageable) {
        List<UserInfoResponseDto> contents = memberRepository.getContent(username,pageable).stream().map(i -> UserInfoResponseDto.of(i)).collect(Collectors.toList());
        return memberRepository.adminUserBoardList(username,contents, pageable);


    }

    public AdminDashBoardResponseDto adminDashBoardInfo(Long id, MemberListRequestDto memberListRequestDto, Pageable pageable) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));
        Page<ListResponseDto> listResponseDtos = myPageQueryRepository.MyPageListPage(memberListRequestDto, pageable, null);
        return AdminDashBoardResponseDto.of(member, listResponseDtos);
    }
}

