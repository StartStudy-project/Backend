package com.study.studyproject.board.service;

import com.study.studyproject.board.dto.BoardChangeRecruitRequestDto;
import com.study.studyproject.board.dto.BoardOneResponseDto;
import com.study.studyproject.board.dto.BoardReUpdateRequestDto;
import com.study.studyproject.board.dto.BoardWriteRequestDto;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Role;
import com.study.studyproject.global.GlobalResultDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface BoardService {
    GlobalResultDto boardSave(BoardWriteRequestDto boardWriteRequestDto, Long memberId);

     GlobalResultDto updateWrite(BoardReUpdateRequestDto boardReUpdateRequestDto);

     BoardOneResponseDto boardOne(Long boardId, String memberId, HttpServletRequest request, HttpServletResponse response);


     GlobalResultDto boardDeleteOne(Long boardId, Role role);

     GlobalResultDto changeRecruit(BoardChangeRecruitRequestDto boardChangeRecruitRequestDto);

}
