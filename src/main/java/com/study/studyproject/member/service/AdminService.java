package com.study.studyproject.member.service;


import com.study.studyproject.entity.Member;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {

    Page<UserInfoResponseDto> userInfoAll(String usernam,Pageable pageable);
}
