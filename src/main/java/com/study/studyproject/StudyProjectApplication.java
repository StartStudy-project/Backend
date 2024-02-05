package com.study.studyproject;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.*;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.reply.repository.ReplyRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class StudyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyProjectApplication.class, args);
	}







}
