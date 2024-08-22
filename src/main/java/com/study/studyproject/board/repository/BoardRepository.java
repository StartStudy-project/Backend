package com.study.studyproject.board.repository;

import com.study.studyproject.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>  {
}
