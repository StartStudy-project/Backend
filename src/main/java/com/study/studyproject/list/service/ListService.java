package com.study.studyproject.list.service;

import com.study.studyproject.list.dto.ListResponseDto;
import com.study.studyproject.list.dto.MainRequest;
import com.study.studyproject.list.repository.MainQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ListService {

    private final MainQueryRepository mainQueryRepository;

    //조회
    public Page<ListResponseDto> list(String title, MainRequest boardListPage, Pageable pageable) {
        return mainQueryRepository.getBoardListPage(title,boardListPage, pageable);
    }

}
