package com.study.studyproject.reply.repository;

import com.study.studyproject.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply,Long>, ReplyRepositoryCustom{

}
