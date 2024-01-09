package com.study.studyproject.list.service;

import com.study.studyproject.board.dto.ListResponseDto;
import com.study.studyproject.board.dto.MainRequest;
import com.study.studyproject.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ListService {

    private final BoardRepository boardRepository;

    //조회
    public Page<ListResponseDto> list(MainRequest boardListPage, Pageable pageable) {
        Page<ListResponseDto> listResponseDtos = boardRepository.boardListPage(boardListPage, pageable);
        return listResponseDtos;
    }

}
