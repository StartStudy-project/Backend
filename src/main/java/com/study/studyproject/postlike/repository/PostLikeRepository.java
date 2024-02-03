package com.study.studyproject.postlike.repository;

import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>  {
}
