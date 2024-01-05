package com.study.studyproject.login.repository;


import com.study.studyproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Member, Long> {
}
