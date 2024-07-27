package com.study.studyproject.login.repository;


import com.study.studyproject.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;


import java.util.Optional;

public interface RefreshRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByEmail(String email);

}
