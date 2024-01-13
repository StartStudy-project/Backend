package com.study.studyproject.member.service;

import com.study.studyproject.list.dto.ListResponseDto;
import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Member;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.login.repository.RefreshRepository;
import com.study.studyproject.member.dto.MemberListRequestDto;
import com.study.studyproject.member.dto.MemberUpdateResDto;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.member.repository.MyPageQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Service
@org.springframework.transaction.annotation.Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MyPageQueryRepository myPageQueryRepository;
    private final JwtUtil jwtUtil;

    //사용자 정보조회
    @Override
    @Transactional(readOnly  = true)
    public UserInfoResponseDto userInfoService(String token) {
        Long idFromToken = jwtUtil.getIdFromToken(token);
        System.out.println("idFromToken = " + idFromToken);
        Member member = memberRepository.findById(idFromToken).orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));

        return UserInfoResponseDto.of(member);

    }

    @Override
    public GlobalResultDto userInfoUpdate(String token, MemberUpdateResDto memberUpdateResDto) {
        Long idFromToken = jwtUtil.getIdFromToken(token);
        Member member = memberRepository.findById(idFromToken).orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));
        member.updateMemberInfo(memberUpdateResDto);

        return new GlobalResultDto("사용자 수정 성공", HttpStatus.OK.value());


    }

    @Override
    public Page<ListResponseDto> listMember(String token, MemberListRequestDto memberListRequestDto, Pageable pageable) {
        Long idFromToken = jwtUtil.getIdFromToken(token);
        memberListRequestDto.setMemberId(idFromToken);
        return myPageQueryRepository.MyPageListPage(memberListRequestDto, pageable);
    }


}


