package com.study.studyproject.postlike.repository;

import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long>  {

    @Query("SELECT pl FROM PostLike pl WHERE pl.board.id = :boardId")
    List<PostLike> findByBoardId(@Param("boardId") Long boardId);

    Optional<PostLike> findByBoardAndMember(Board board, Member member);

    int countByBoardAndMember(Board board, Member member);


}
