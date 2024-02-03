package com.study.studyproject.postlike.service;


import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.PostLike;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.member.dto.UserInfoResponseDto;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.postlike.repository.PostLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final BoardRepository boardRepository;

    public GlobalResultDto postLikeSave(Long boardId ,Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Board board = boardRepository.findById(boardId).orElseThrow();
        postLikeRepository.save(PostLike.create(member, board));
        return new GlobalResultDto("관심글이 추가되었습니다.", HttpStatus.OK.value());
    }

    public GlobalResultDto postLikeDelete(Long postLikeId) {
        postLikeRepository.deleteById(postLikeId);
        return new GlobalResultDto("관심글이 삭제되었습니다.", HttpStatus.OK.value());
    }
}
