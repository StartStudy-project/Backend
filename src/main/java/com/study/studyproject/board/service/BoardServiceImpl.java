package com.study.studyproject.board.service;

import com.study.studyproject.board.dto.*;
import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.domain.*;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.auth.UserDetailsImpl;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.postlike.repository.PostLikeRepository;
import com.study.studyproject.reply.dto.ReplyInfoResponseDto;
import com.study.studyproject.reply.dto.ReplyResponseDto;
import com.study.studyproject.reply.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.study.studyproject.global.exception.ex.ErrorCode.*;
import static com.study.studyproject.reply.dto.ReplyInfoResponseDto.convertReplyToDto;
import static com.study.studyproject.reply.dto.ReplyResponseDto.ReplyResponseToDto;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;

    //작성
    @Override
    public GlobalResultDto boardSave(BoardWriteRequestDto boardWriteRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(NOT_FOUND_BOARD));
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
    public BoardOneResponseDto boardOne(Long boardId, UserDetailsImpl userDetails) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException(NOT_FOUND_BOARD));
        String postLike = getPostLike(userDetails, board);
        Long postLikeId = getPostLikeId(userDetails, board);
        ReplyResponseDto replies = findReplies(boardId);
        return BoardOneResponseDto.of(postLikeId,postLike, board, replies,userDetails.getNickname());
    }

    private Long getPostLikeId(UserDetailsImpl userDetails, Board board) {
        Optional<PostLike> findByPostLike = postLikeRepository.findByBoardAndMember(board, userDetails.getMember());
        if (findByPostLike.isPresent()) {
            return findByPostLike.get().getId();
        }
        return 0L;

    }

    @Transactional
    public void updateView(
    Long boardId){
        if (!boardRepository.existsById(boardId)) {
            throw new NotFoundException(NOT_FOUND_BOARD);
        }
        boardRepository.updateHits(boardId);
    }


    private String getPostLike(UserDetailsImpl userDetails, Board board) {
        if (Role.containsLoginRoleType(userDetails.getAuthority())) {
            Optional<PostLike> byBoardAndMember = postLikeRepository.findByBoardAndMember(board, userDetails.getMember());
            return byBoardAndMember.isPresent() ? PostLikeState.LIKING.getName() : PostLikeState.LIKE.getName();
        }
        return null;
    }


    private ReplyResponseDto findReplies(Long boardId) {
        List<Reply> comments = replyRepository.findByBoardReply(boardId);
        List<ReplyInfoResponseDto> commentResponseDTOList = getReplyInfoResponseDtos(comments);
        return ReplyResponseToDto(replyRepository.findBoardReplyCnt(boardId), commentResponseDTOList);
    }

    private static List<ReplyInfoResponseDto> getReplyInfoResponseDtos(List<Reply> comments) {

        List<ReplyInfoResponseDto> commentResponseDTOList = new ArrayList<>();
        Map<Long, ReplyInfoResponseDto> commentDTOHashMap = new HashMap<>();

        comments.forEach(c -> {
            ReplyInfoResponseDto commentResponseDTO = convertReplyToDto(c);
            commentDTOHashMap.put(commentResponseDTO.getReplyId(), commentResponseDTO);
            if (c.getParent() != null)
                commentDTOHashMap.get(c.getParent().getId()).getChildren().add(commentResponseDTO);
            else commentResponseDTOList.add(commentResponseDTO);
        });

        return commentResponseDTOList;
    }


    //삭제
    @Override
    public GlobalResultDto boardDeleteOne(Long boardId, Role role) {
        if (isAdmin(role)) {
            Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException(NOT_FOUND_BOARD));
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
