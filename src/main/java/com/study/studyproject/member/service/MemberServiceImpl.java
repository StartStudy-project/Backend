package com.study.studyproject.member.service;

import com.study.studyproject.list.dto.ListResponseDto;
import com.study.studyproject.entity.Member;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import com.study.studyproject.member.dto.MemberListRequestDto;
import com.study.studyproject.member.dto.MemberUpdateResDto;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.member.repository.MyPagePostLikeQueryRepository;
import com.study.studyproject.member.repository.MyPageQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@org.springframework.transaction.annotation.Transactional
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MyPageQueryRepository myPageQueryRepository;
    private final MyPagePostLikeQueryRepository myPagePostLikeQueryRepository;
    private final String  getMember= "member";

    //사용자 정보조회
    @Override
    @Transactional(readOnly  = true)
    public UserInfoResponseDto userInfoService(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));
        return UserInfoResponseDto.of(member);

    }

    @Override
    public GlobalResultDto userInfoUpdate(Long memberId , MemberUpdateResDto memberUpdateResDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));
        member.updateMemberInfo(memberUpdateResDto);

        return new GlobalResultDto("사용자 수정 성공", HttpStatus.OK.value());


    }

    @Override
    public Page<ListResponseDto> listMember(Long token, MemberListRequestDto memberListRequestDto, Pageable pageable) {
        return myPageQueryRepository.MyPageListPage(memberListRequestDto, pageable,token, getMember);
    }


    public Page<ListResponseDto> postLikeBoard(Long memberId, MemberListRequestDto memberListRequestDto, Pageable pageable) {
        return myPagePostLikeQueryRepository.MyPageListPage(memberListRequestDto, pageable,memberId);
    }
}


