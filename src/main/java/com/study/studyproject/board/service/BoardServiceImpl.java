package com.study.studyproject.board.service;

import com.study.studyproject.board.dto.*;
import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.*;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.ErrorCode;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.postlike.repository.PostLikeRepository;
import com.study.studyproject.reply.dto.ReplyInfoResponseDto;
import com.study.studyproject.reply.dto.ReplyResponseDto;
import com.study.studyproject.reply.repository.ReplyRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.study.studyproject.global.exception.ex.ErrorCode.*;
import static com.study.studyproject.reply.dto.ReplyInfoResponseDto.convertReplyToDto;
import static com.study.studyproject.reply.dto.ReplyResponseDto.ReplyResponsetoDto;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final JwtUtil jwtUtil;


    //작성
    @Override
    public GlobalResultDto boardSave(BoardWriteRequestDto boardWriteRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(NOT_FOUND_BOARD)) ;
        Board entity = boardWriteRequestDto.toEntity(member);
        boardRepository.save(entity);
        return new GlobalResultDto("글 작성 완료", HttpStatus.OK.value());

    }

    //수정
    @Override
    public GlobalResultDto updateWrite(BoardReUpdateRequestDto boardReUpdateRequestDto) {
        Board board = boardRepository.findById(boardReUpdateRequestDto.getBoardId()).orElseThrow(() -> new NotFoundException(NOT_FOUND_BOARD));
        board.updateBoard(boardReUpdateRequestDto);
        return new GlobalResultDto("글 작성 완료", HttpStatus.OK.value());
    }



    //글 1개만 가져오기
    @Override
    public BoardOneResponseDto boardOne(Long boardId, String token, HttpServletRequest request, HttpServletResponse response) {

        Board board = boardRepository.findById(boardId).orElseThrow(()->new NotFoundException(NOT_FOUND_BOARD));

        Long currentMemberId = 0L;


        String postLike = "";
        if (token != null) {
            currentMemberId = jwtUtil.getIdFromToken(token);
            Member member = memberRepository.findById(currentMemberId).orElseThrow(()->new NotFoundException(NOT_FOUND_MEMBER));
            Optional<PostLike> postLikeOne = postLikeRepository.findByBoardAndMember(board, member);
            postLike = "관심";
            if (postLikeOne.isPresent()) {
                postLike = "관심완료";
            }
        };

        ReplyResponseDto replies = findReplies(boardId,currentMemberId);

        return BoardOneResponseDto.of(currentMemberId,postLike,board,replies);

    }



    private ReplyResponseDto findReplies(Long boardId,Long currentMemberId) {
           List<Reply> comments = replyRepository.findByBoardReply(boardId);
           List<ReplyInfoResponseDto> commentResponseDTOList = getReplyInfoResponseDtos(comments,currentMemberId);
        return ReplyResponsetoDto(replyRepository.findBoardReplyCnt(boardId), commentResponseDTOList);
    }

    private static List<ReplyInfoResponseDto> getReplyInfoResponseDtos(List<Reply> comments,Long currentMemberId) {

        List<ReplyInfoResponseDto> commentResponseDTOList = new ArrayList<>();
        Map<Long, ReplyInfoResponseDto> commentDTOHashMap = new HashMap<>();

        comments.forEach(c -> {
            ReplyInfoResponseDto commentResponseDTO = convertReplyToDto(c,currentMemberId);
            commentDTOHashMap.put(commentResponseDTO.getReplyId(), commentResponseDTO);
            if (c.getParent() != null) commentDTOHashMap.get(c.getParent().getId()).getChildren().add(commentResponseDTO);
            else commentResponseDTOList.add(commentResponseDTO);
        });

        return commentResponseDTOList;
    }



    //삭제
    @Override
    public GlobalResultDto boardDeleteOne(Long boardId, Role role) {
        if (isAdmin(role)) {
            Board board = boardRepository.findById(boardId).orElseThrow(()->new NotFoundException(NOT_FOUND_BOARD) );
            board.changeAdminDeleteBoard();
            return new GlobalResultDto("관리자 권한으로 게시글 삭제 완료", HttpStatus.OK.value());
        }

        //댓글
        List<Reply> replies = replyRepository.findByBoardReplies(boardId);
        //postLike
        List<PostLike> postLikes = postLikeRepository.findByBoardId(boardId);


        validateDeleteBoard(replies, postLikes);

        boardRepository.deleteById(boardId);
        return new GlobalResultDto("게시글 삭제 완료", HttpStatus.OK.value());
    }

    private static void validateDeleteBoard(List<Reply> replies, List<PostLike> postLikes) throws NotFoundException {
        if (isReplies(replies) || isPostLikes(postLikes)) {
            throw new NotFoundException(UNABLE_DELETE_BOARD);
        }
    }

    private static boolean isPostLikes(List<PostLike> postLikes) {
        return postLikes.size() != 0;
    }

    private static boolean isReplies(List<Reply> replies) {
        return replies.size() != 0;
    }

    private static boolean isAdmin(Role role) {
        return role == Role.ROLE_ADMIN;
    }

    //모집 구분 변경
    @Override
    public GlobalResultDto changeRecruit(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException(NOT_FOUND_BOARD));
        board.changeRecuritBoard();
        return new GlobalResultDto("모집 구분 변경 완료", HttpStatus.OK.value());
    }
}
