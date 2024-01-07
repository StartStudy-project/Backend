package com.study.studyproject.board.service;

import com.study.studyproject.board.dto.BoardReUpdateRequestDto;
import com.study.studyproject.board.dto.BoardWriteRequestDto;
import com.study.studyproject.board.dto.MainReqestDto;
import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public GlobalResultDto boardSave(BoardWriteRequestDto boardWriteRequestDto, String token) {
        Member member = memberRepository.findById(jwtUtil.getIdFromToken(token))
                .orElseThrow(() -> new IllegalArgumentException("ID가 없습니다."));
        Board entity = boardWriteRequestDto.toEntity(member);
        boardRepository.save(entity);


        return  new GlobalResultDto("글 작성 완료", HttpStatus.OK.value());

    }

    public void updateWrite(BoardReUpdateRequestDto boardReUpdateRequestDto) {

        Board board = boardRepository.findById(boardReUpdateRequestDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("작성된 게시글이 없습니다."));
        board.updateBoard(boardReUpdateRequestDto);

    }

    //메인 서비스 가져오기
    public void BoardService(MainReqestDto mainReqestDto, int page, Pageable pageable) {



    }
}
