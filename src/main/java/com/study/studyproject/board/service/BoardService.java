package com.study.studyproject.board.service;

import com.study.studyproject.board.dto.*;
import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Reply;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.reply.dto.ReplyInfoDto;
import com.study.studyproject.reply.dto.ReplyResponseDto;
import com.study.studyproject.reply.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.study.studyproject.reply.dto.ReplyInfoDto.convertReplyToDto;
import static com.study.studyproject.reply.dto.ReplyResponseDto.ReplyResponsetoDto;

@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;



    //작성
    public GlobalResultDto boardSave(BoardWriteRequestDto boardWriteRequestDto, String token) {
        System.out.println("안녕");
        Member member = memberRepository.findById(jwtUtil.getIdFromToken(token))
                .orElseThrow(() -> new IllegalArgumentException("ID가 없습니다."));
        System.out.println("member = " + member);
        Board entity = boardWriteRequestDto.toEntity(member);
        boardRepository.save(entity);
        System.out.println("작성");
        return new GlobalResultDto("글 작성 완료", HttpStatus.OK.value());

    }

    //수정
    public void updateWrite(BoardReUpdateRequestDto boardReUpdateRequestDto) {
        System.out.println("boardReUpdateRequestDto = " + boardReUpdateRequestDto);
        Board board = boardRepository.findById(boardReUpdateRequestDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("작성된 게시글이 없습니다."));
        System.out.println("board = " + board);
        board.updateBoard(boardReUpdateRequestDto);
    }


    //글 1개만 가져오기
    public BoardOneResponseDto boardOne(String token, Long boardId) {
        Board board = boardRepository.findById(boardId ).orElseThrow(() -> new IllegalArgumentException("게시판이 없습니다."));
        board.updateViewCnt(board.getViewCount());
        ReplyResponseDto replies = findReplies(token, boardId);
        return BoardOneResponseDto.of(board,replies);

    }

    private ReplyResponseDto findReplies(String token, Long boardId) {
        Long memberId = jwtUtil.getIdFromToken(token);
        List<Reply> comments = replyRepository.findByBoardReply(boardId);
        List<ReplyInfoDto> commentResponseDTOList = getReplyInfoDtos(comments, memberId);
        return ReplyResponsetoDto(commentResponseDTOList.size(), commentResponseDTOList);
    }

    private static List<ReplyInfoDto> getReplyInfoDtos(List<Reply> comments, Long memberId) {
        List<ReplyInfoDto> commentResponseDTOList = new ArrayList<>();
        Map<Long, ReplyInfoDto> commentDTOHashMap = new HashMap<>();

        comments.forEach(c -> {
            ReplyInfoDto commentResponseDTO = convertReplyToDto(c, memberId);
            commentDTOHashMap.put(commentResponseDTO.getReplyId(), commentResponseDTO);
            if (c.getParent() != null) commentDTOHashMap.get(c.getParent().getId()).getChildren().add(commentResponseDTO);
            else commentResponseDTOList.add(commentResponseDTO);
        });
        return commentResponseDTOList;
    }



    //삭제
    public GlobalResultDto boardDeleteOne(Long boardId) {
        boardRepository.deleteById(boardId);
        return new GlobalResultDto("게시글 삭제 완료", HttpStatus.OK.value());
    }

    //모집 구분 변경
    public GlobalResultDto changeRecruit(BoardChangeRecruitRequestDto boardChangeRecruitRequestDto) {
        Board board = boardRepository.findById(boardChangeRecruitRequestDto.getBoardId() ).orElseThrow(() -> new IllegalArgumentException("게시판이 없습니다."));
        board.ChangeRecuritBoard(boardChangeRecruitRequestDto);
        return new GlobalResultDto("모집 구분 변경 완료", HttpStatus.OK.value());
    }
}
