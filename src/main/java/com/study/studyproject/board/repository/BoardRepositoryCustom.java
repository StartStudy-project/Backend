package com.study.studyproject.board.repository;

import com.study.studyproject.board.dto.ListRequestDto;
import com.study.studyproject.board.dto.ListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<ListResponseDto> boardListPage(ListRequestDto condition, Pageable pageable);
//

}
