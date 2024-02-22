package com.study.studyproject.board.service;

import com.study.studyproject.board.dto.*;
import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.*;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
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
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.study.studyproject.reply.dto.ReplyInfoResponseDto.convertReplyToDto;
import static com.study.studyproject.reply.dto.ReplyResponseDto.ReplyResponsetoDto;

@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final JwtUtil jwtUtil;

    private final static String VIEWCOOKIENAME = "alreadyViewCookie";

    //작성
    public GlobalResultDto boardSave(BoardWriteRequestDto boardWriteRequestDto, Long idFromToken) {
        Member member = memberRepository.findById(idFromToken).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        Board entity = boardWriteRequestDto.toEntity(member);
        boardRepository.save(entity);
        return new GlobalResultDto("글 작성 완료", HttpStatus.OK.value());

    }

    //수정
    public GlobalResultDto updateWrite(BoardReUpdateRequestDto boardReUpdateRequestDto) {
        Board board = boardRepository.findById(boardReUpdateRequestDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("작성된 게시글이 없습니다."));
        board.updateBoard(boardReUpdateRequestDto);
        return new GlobalResultDto("글 작성 완료", HttpStatus.OK.value());
    }
    private Cookie createCookieForForNotOverlap(Long postId) {
        Cookie cookie = new Cookie(VIEWCOOKIENAME + postId, String.valueOf(postId));
        cookie.setMaxAge(getRemainSecondForTommorow());
        cookie.setHttpOnly(true);
        return cookie;
    }

    // 다음 날 정각까지 남은 시간(초)
    private int getRemainSecondForTommorow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tommorow = LocalDateTime.now().plusDays(1L).truncatedTo(ChronoUnit.DAYS);
        return (int) now.until(tommorow, ChronoUnit.SECONDS);
    }


    //글 1개만 가져오기
    public BoardOneResponseDto boardOne(Long boardId, String token, HttpServletRequest request, HttpServletResponse response) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판이 없습니다."));
        checkDuplicate(boardId, request, response, board);


        Long currentMemberId = 0L;
        String postLike = "관심중";
        Long PostLikeId =0L;
        if (token != null) {
            currentMemberId = jwtUtil.getIdFromToken(token);
            Member member = memberRepository.findById(currentMemberId).orElseThrow(() -> new UserNotFoundException("사용자 없음"));
            Optional<PostLike> postLikeOne = postLikeRepository.findByBoardAndMember(board, member);
            if (postLikeOne.isPresent()) {
                postLike = "관심 완료";
                PostLikeId = postLikeOne.get().getId();
            }
        }

        ReplyResponseDto replies = findReplies(boardId,currentMemberId);
        return BoardOneResponseDto.of(PostLikeId,postLike,board,replies,currentMemberId);

    }

    private void checkDuplicate(Long boardId, HttpServletRequest request, HttpServletResponse response, Board board) {
        Cookie[] cookies = request.getCookies();
        boolean checkCookie = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 이미 조회를 한 경우 체크
                if (cookie.getName().equals(VIEWCOOKIENAME + boardId)) checkCookie = true;

            }
            if (!checkCookie) {
                Cookie newCookie = createCookieForForNotOverlap(boardId);
                response.addCookie(newCookie);
                board.updateViewCnt(board.getViewCount());
            }
        } else {
            Cookie newCookie = createCookieForForNotOverlap(boardId);
            response.addCookie(newCookie);
            board.updateViewCnt(board.getViewCount());
        }
    }

    private ReplyResponseDto findReplies(Long boardId, Long currentMemberId) {
           List<Reply> comments = replyRepository.findByBoardReply(boardId);
           List<ReplyInfoResponseDto> commentResponseDTOList = getReplyInfoResponseDtos(comments,currentMemberId);
        return ReplyResponsetoDto(replyRepository.findBoardReplyCnt(boardId), commentResponseDTOList);
    }

    private static List<ReplyInfoResponseDto> getReplyInfoResponseDtos(List<Reply> comments, Long currentMemberId) {

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
    public GlobalResultDto boardDeleteOne(Long boardId, Role role) {

        if (role.equals(Role.ROLE_ADMIN)) {
            Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판이 없습니다."));
            board.ChangeBoardIsDeleted(true);
            board.deleteBoardContent("관리자로 의해 게시글 삭제","관리자로 의해 게시글 삭제되었습니다.");
            return new GlobalResultDto("관리자 권한으로 게시글 삭제 완료", HttpStatus.OK.value());
        }

        //댓글
        List<Reply> replies = replyRepository.findByBoardReplies(boardId);
        //postLike
        List<PostLike> postLikes = postLikeRepository.findByBoardId(boardId);

        if (replies.size() != 0 || postLikes.size() != 0) {
            return new GlobalResultDto("게시글을 삭제 할 수 없습니다.", HttpStatus.FORBIDDEN.value());
        }

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
