package com.study.studyproject.member.repository;


import com.study.studyproject.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String email);

    boolean existsByNickname(String nickname);

}
