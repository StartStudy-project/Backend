package com.study.studyproject.postlike.service;


import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.domain.Board;
import com.study.studyproject.domain.Member;
import com.study.studyproject.domain.PostLike;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.BadRequestException;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.postlike.repository.PostLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.study.studyproject.global.exception.ex.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final BoardRepository boardRepository;

    public GlobalResultDto postLikeSave(Long boardId ,Member member) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new NotFoundException(NOT_FOUND_BOARD));
        Optional<PostLike> postLike = postLikeRepository.findByBoardAndMember(board, member);
        if (postLike.isPresent()) {
            throw new BadRequestException(POST_LIKE_DUPLICATED);
        }
        postLikeRepository.save(PostLike.create(member, board));
        return new GlobalResultDto("관심글이 추가되었습니다.", HttpStatus.OK.value());
    }

    public GlobalResultDto postLikeDelete(Long postLikeId) {
        postLikeRepository.deleteById(postLikeId);
        return new GlobalResultDto("관심글이 삭제되었습니다.", HttpStatus.OK.value());
    }
}
