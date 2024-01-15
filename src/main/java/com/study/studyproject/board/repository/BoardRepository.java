package com.study.studyproject.board.repository;

import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>  {
}
