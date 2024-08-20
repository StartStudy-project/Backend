package com.study.studyproject.board.service;

import com.study.studyproject.board.dto.BoardOneResponseDto;
import com.study.studyproject.board.dto.BoardReUpdateRequestDto;
import com.study.studyproject.board.dto.BoardWriteRequestDto;
import com.study.studyproject.entity.Role;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.auth.UserDetailsImpl;

public interface BoardService {
    GlobalResultDto boardSave(BoardWriteRequestDto boardWriteRequestDto, Long memberId);

     GlobalResultDto updateWrite(BoardReUpdateRequestDto boardReUpdateRequestDto);

     BoardOneResponseDto boardOne(Long boardId, UserDetailsImpl userDetails);


     GlobalResultDto boardDeleteOne(Long boardId, Role role);

     GlobalResultDto changeRecruit(Long boardId);

}
