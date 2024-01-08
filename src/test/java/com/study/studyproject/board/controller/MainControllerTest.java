//package com.study.studyproject.board.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.study.studyproject.ControllerTestSupport;
//import com.study.studyproject.board.dto.MainRequest;
//import com.study.studyproject.board.repository.BoardRepository;
//import com.study.studyproject.board.repository.BoardRepositoryImpl;
//import com.study.studyproject.entity.*;
//import com.study.studyproject.member.repository.MemberRepository;
//import com.study.studyproject.reply.repository.ReplyRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//class MainControllerTest extends ControllerTestSupport {
//
//
//    @Autowired
//    BoardRepositoryImpl boardRepositoryImpl;
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    BoardRepository boardRepository;
//    @Autowired
//    ReplyRepository replyRepository;
//
//
//    @BeforeEach
//    void setUp() {
//            memberRepository.save(Member.builder()
//                    .email("jac")
//                    .role(Role.ROLE_USER)
//                    .password("1234")
//                    .build());
//
//            Member ja2c = memberRepository.save(Member.builder()
//                    .email("ja2c")
//                    .role(Role.ROLE_USER)
//                    .password("12324")
//                    .build());
//
//            memberRepository.save(Member.builder()
//                    .email("jac")
//                    .role(Role.ROLE_USER)
//                    .password("12334")
//                    .build());
//            Board build = Board.builder()
//                    .content("내용")
//                    .title("제목")
//                    .nickname("ac")
//                    .category(Category.CS)
//                    .member(ja2c)
//                    .build();
//
//            boardRepository.save(build);
//
//            Board build1 = Board.builder()
//                    .content("내용")
//                    .title("제목")
//                    .nickname("ac")
//                    .category(Category.CS)
//                    .member(ja2c)
//                    .build();
//
//            boardRepository.save(build1);
//
//            Board build2 = Board.builder()
//                    .content("내용")
//                    .title("제목")
//                    .nickname("ac")
//                    .category(Category.CS)
//                    .member(ja2c)
//                    .build();
//            boardRepository.save(build2);
//
//
//            Board build3 = Board.builder()
//                    .content("내용")
//                    .title("제목")
//                    .nickname("ac")
//                    .category(Category.CS)
//                    .member(ja2c)
//                    .build();
//            boardRepository.save(build3);
//
//
//            Reply d = Reply.builder()
//                    .board(build1)
//                    .content("d")
//                    .build();
//
//            replyRepository.save(d);
//
//
//            Reply d2 = Reply.builder()
//                    .board(build)
//                    .content("d")
//                    .build();
//
//            replyRepository.save(d2);
//
//
//    }
//
//    @Test
//    void mainList() throws Exception {
//        MainRequest request = MainRequest.builder()
//                .type(Recruit.모집중)
//                .order(1)
//                .category(Category.CS)
//                .build();
//
//
//
//        // when & then
//        mockMvc.perform(get("/")
//                        .content(objectMapper.writeValueAsString(request))
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("200"))
//                .andExpect(jsonPath("$.status").value("OK"))
//                .andExpect(jsonPath("$.message").value("OK"))
//                .andDo(print());
//    }
//
//
//
//}