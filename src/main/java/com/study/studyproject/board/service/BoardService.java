package com.study.studyproject.board.service;

import com.querydsl.core.Tuple;
import com.study.studyproject.board.dto.*;
import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.board.repository.BoardRepositoryCustom;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Reply;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.reply.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;



    public GlobalResultDto boardSave(BoardWriteRequestDto boardWriteRequestDto, String token) {
        System.out.println("boardWriteRequestDto = " + boardWriteRequestDto);
        Member member = memberRepository.findById(jwtUtil.getIdFromToken(token))
                .orElseThrow(() -> new IllegalArgumentException("ID가 없습니다."));
        Board entity = boardWriteRequestDto.toEntity(member);
        boardRepository.save(entity);


        return new GlobalResultDto("글 작성 완료", HttpStatus.OK.value());

    }

    //수정
    public void updateWrite(BoardReUpdateRequestDto boardReUpdateRequestDto) {
        System.out.println("boardReUpdateRequestDto = " + boardReUpdateRequestDto);
        Board board = boardRepository.findById(boardReUpdateRequestDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("작성된 게시글이 없습니다."));
        System.out.println("board = " + board);
        board.updateBoard(boardReUpdateRequestDto);
    }




//
    //글 1개ㅁ만 가져오기
    public BoardOneResponseDto boardOne(Long boardId) {
        Board board = boardRepository.findById(boardId ).orElseThrow(() -> new IllegalArgumentException("게시판이 없습니다."));
        board.updateViewCnt(board.getViewCount());
        return BoardOneResponseDto.of(board);

    }

    //삭제
    public GlobalResultDto boardDeleteOne(Long boardId) {
        boardRepository.deleteById(boardId);
        return new GlobalResultDto("게시글 삭제 완료", HttpStatus.OK.value());
    }
}
