package com.study.studyproject.login.repository;


import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {
}
