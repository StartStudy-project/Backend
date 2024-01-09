package com.study.studyproject.member.service;


import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.member.dto.MemberUpdateResDto;
import com.study.studyproject.member.dto.UserInfoResponseDto;

public interface MemberService {


    //사용자 정보 조회
    UserInfoResponseDto userInfoService(String token);


    //수정
    GlobalResultDto userInfoUpdate(String token, MemberUpdateResDto memberUpdateResDto);


    //




}
