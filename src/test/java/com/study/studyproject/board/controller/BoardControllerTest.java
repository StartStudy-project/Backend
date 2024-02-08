package com.study.studyproject.board.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.studyproject.board.dto.BoardChangeRecruitRequestDto;
import com.study.studyproject.board.dto.BoardReUpdateRequestDto;
import com.study.studyproject.board.dto.BoardWriteRequestDto;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.list.dto.MainRequest;
import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.*;
import com.study.studyproject.login.dto.TokenDtoResponse;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.reply.repository.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.study.studyproject.entity.Category.CS;
import static com.study.studyproject.entity.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class BoardControllerTest  {


    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtUtil jwtUtil;


    @Test
    @DisplayName("게시글을 작성한다.")
    void writingTest() throws Exception {
        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임0");
        memberRepository.save(member1);

        TokenDtoResponse allToken = jwtUtil.createAllToken(member1.getEmail(), member1.getId());


        BoardWriteRequestDto requestDto = BoardWriteRequestDto.builder()
                .title("제목1")
                .category(Category.CS)
                .nickname("닉네임")
                .content("내용")
                .build();


        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/board/member/writing")
                        .header(jwtUtil.ACCESS_TOKEN, jwtUtil.BEARER + allToken.getAccessToken())
                        .header(jwtUtil.REFRESH_TOKEN, jwtUtil.BEARER + allToken.getRefreshToken())
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("200"))
                .andExpect(jsonPath("$.message").value("글 작성 완료"))
                .andDo(print());
    }


    @Test
    @DisplayName("게시글을 작성할 경우 제목, 내용, 카테고리, 닉네임은 필수값입니다.")
    void writingTestWithoutContentAndCategory() throws Exception {
        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임0");
        memberRepository.save(member1);

        TokenDtoResponse allToken = jwtUtil.createAllToken(member1.getEmail(), member1.getId());


        BoardWriteRequestDto requestDto = BoardWriteRequestDto.builder()
                .build();




        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/board/member/writing")
                        .header(jwtUtil.ACCESS_TOKEN, jwtUtil.BEARER + allToken.getAccessToken())
                        .header(jwtUtil.REFRESH_TOKEN, jwtUtil.BEARER + allToken.getRefreshToken())
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.category").value("카테고리를 입력해주세요"))
                .andExpect(jsonPath("$.content").value("내용을 입력해주세요"))
                .andExpect(jsonPath("$.title").value("제목을 입력해주세요"))
                .andExpect(jsonPath("$.nickname").value("닉네임 입력해주세요"))
                .andDo(print());
    }


    @Test
    @DisplayName("게시글을 수정할 경우 게시글 번호와 내용은 필수값이다.")
    void updateWritingWithoutId() throws Exception {
        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임0");
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(board);
        TokenDtoResponse allToken = jwtUtil.createAllToken(member1.getEmail(), member1.getId());
        BoardReUpdateRequestDto requestDto = BoardReUpdateRequestDto.builder()
                .category(CS)
                .title("타이틀")
                .build();


        // when & then
        mockMvc.perform(patch("/board/member/updateWrite")
                        .header(jwtUtil.ACCESS_TOKEN, jwtUtil.BEARER + allToken.getAccessToken())
                        .header(jwtUtil.REFRESH_TOKEN, jwtUtil.BEARER + allToken.getRefreshToken())
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.boardId").value("게시글 번호를 입력해주세요"))
                .andExpect(jsonPath("$.content").value("내용을 입력해주세요"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글을 수정한다.")
    void updateWritingTest() throws Exception {
        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임0");
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(board);
        TokenDtoResponse allToken = jwtUtil.createAllToken(member1.getEmail(), member1.getId());
        BoardReUpdateRequestDto requestDto = BoardReUpdateRequestDto.builder()
                .boardId(board.getId())
                .category(CS)
                .title("타이틀")
                .content("내용")
                .build();


        // when & then
        mockMvc.perform(patch("/board/member/updateWrite")
                        .header(jwtUtil.ACCESS_TOKEN, jwtUtil.BEARER + allToken.getAccessToken())
                        .header(jwtUtil.REFRESH_TOKEN, jwtUtil.BEARER + allToken.getRefreshToken())
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("모집구분 변경한다.")
    void changeRecruitTest() throws Exception {
        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임0");
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(board);
        TokenDtoResponse allToken = jwtUtil.createAllToken(member1.getEmail(), member1.getId());
        BoardChangeRecruitRequestDto requestDto = BoardChangeRecruitRequestDto.builder()
                .boardId(board.getId())
                .recruit(Recruit.모집완료)
                .build();


        // when & then
        mockMvc.perform(patch("/board/member/changeRecruit")
                        .header(jwtUtil.ACCESS_TOKEN, jwtUtil.BEARER + allToken.getAccessToken())
                        .header(jwtUtil.REFRESH_TOKEN, jwtUtil.BEARER + allToken.getRefreshToken())
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("모집구분 변경할때, 게시글 번호와 모집구분은 필수입니다.")
    void changeRecruitTestWithoutIdAndRecruit() throws Exception {
        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임0");
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(board);
        TokenDtoResponse allToken = jwtUtil.createAllToken(member1.getEmail(), member1.getId());
        BoardChangeRecruitRequestDto requestDto = BoardChangeRecruitRequestDto.builder()
                .build();


        // when & then
        mockMvc.perform(patch("/board/member/changeRecruit")
                        .header(jwtUtil.ACCESS_TOKEN, jwtUtil.BEARER + allToken.getAccessToken())
                        .header(jwtUtil.REFRESH_TOKEN, jwtUtil.BEARER + allToken.getRefreshToken())
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.boardId").value("게시글 번호를 입력해주세요"))
                .andExpect(jsonPath("$.recruit").value("모집구분을 입력해주세요"));

    }

    @Test
    @DisplayName("게시글을 삭제합니다.")
    @WithMockUser
    void deleteBoardTest() throws Exception {
        //given
        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임0");
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(board);
        TokenDtoResponse allToken = jwtUtil.createAllToken(member1.getEmail(), member1.getId());

        //when then
        mockMvc.perform(delete("/board/member/delete")
                        .header(jwtUtil.ACCESS_TOKEN, jwtUtil.BEARER + allToken.getAccessToken())
                        .header(jwtUtil.REFRESH_TOKEN, jwtUtil.BEARER + allToken.getRefreshToken())
                        .param("boardId", String.valueOf(board.getId()))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.message").value("게시글 삭제 완료"));

    }


    @Test
    @DisplayName("관리자 게시글을 삭제합니다.")
    @WithMockUser
    void deleteBoardTestWithAdmin() throws Exception {
        //given
        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임0");
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(board);
        TokenDtoResponse allToken = jwtUtil.createAllToken(member1.getEmail(), member1.getId());

        //when then
        mockMvc.perform(delete("/board/member/delete")
                        .header(jwtUtil.ACCESS_TOKEN, jwtUtil.BEARER + allToken.getAccessToken())
                        .header(jwtUtil.REFRESH_TOKEN, jwtUtil.BEARER + allToken.getRefreshToken())
                        .param("boardId", String.valueOf(board.getId()))
                        .param("role", "ROLE_ADMIN")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.message").value("관리자 권한으로 게시글 삭제 완료"));

    }

    @Test
    @DisplayName("게시글을 상세페이지를 조회한다.")
    @WithMockUser
    void getBoard() throws Exception {
        //given
        Member member1 = createMember("jacom2@naver.com", "!12341234", "사용자명1", "닉네임0");
        Board board = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(board);
        TokenDtoResponse allToken = jwtUtil.createAllToken(member1.getEmail(), member1.getId());

        //when then
        mockMvc.perform(get("/board/"+board.getId())
                        .header(jwtUtil.ACCESS_TOKEN, jwtUtil.BEARER + allToken.getAccessToken())
                        .header(jwtUtil.REFRESH_TOKEN, jwtUtil.BEARER + allToken.getRefreshToken())
                        .param("boardId", String.valueOf(board.getId()))
                )
                .andExpect(status().isOk())
                .andDo(print());

    }



    private Member createMember
            (String email, String password, String username, String nickname) {
        {
            return Member.builder()
                    .nickname(nickname)
                    .username(username)
                    .email(email)
                    .password(password)
                    .role(ROLE_USER).build();
        }


    }
    private Board createBoard(
            Member member, String title, String content, String nickname, Category category
    ) {
        return Board.builder()
                .member(member)
                .title(title)
                .content("내용")
                .nickname(nickname)
                .category(category)
                .build();
    }




}