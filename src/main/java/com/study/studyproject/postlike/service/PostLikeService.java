package com.study.studyproject.postlike.service;


import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.PostLike;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.postlike.repository.PostLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final BoardRepository boardRepository;

    public GlobalResultDto postLikeSave(Long boardId ,Member member) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판이 없습니다."));
        Optional<PostLike> postLike = postLikeRepository.findByBoardAndMember(board, member);
        if (postLike.isPresent()) {
            return new GlobalResultDto("관심글이 이미 추가하였습니다.", HttpStatus.FORBIDDEN.value());
        }
        

        postLikeRepository.save(PostLike.create(member, board));
        return new GlobalResultDto("관심글이 추가되었습니다.", HttpStatus.OK.value());
    }

    public GlobalResultDto postLikeDelete(Long postLikeId) {
        postLikeRepository.deleteById(postLikeId);
        return new GlobalResultDto("관심글이 삭제되었습니다.", HttpStatus.OK.value());
    }
}
