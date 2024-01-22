package com.study.studyproject.reply.service;

import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Reply;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.reply.dto.ReplyInfoDto;
import com.study.studyproject.reply.dto.ReplyRequestDto;
import com.study.studyproject.reply.dto.ReplyResponseDto;
import com.study.studyproject.reply.dto.UpdateReplyRequest;
import com.study.studyproject.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.study.studyproject.reply.dto.ReplyInfoDto.convertReplyToDto;
import static com.study.studyproject.reply.dto.ReplyResponseDto.ReplyResponsetoDto;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {


    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final JwtUtil jwtUtil;



    @Override
    public void insert(String token, ReplyRequestDto replyRequestDto) {
        Long memberId = jwtUtil.getIdFromToken(token);

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UserNotFoundException("사용자가 없습니다"));
        Board board = boardRepository.findById(replyRequestDto.getBoardId())
                .orElseThrow(() -> new NotFoundException("게시글이 없습니다."));

        Reply reply = Reply.toEntity(replyRequestDto, board, member);

        if (replyRequestDto.getParentId() != null) {
            Reply replyParent = replyRepository.findById(replyRequestDto.getParentId())
                    .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
            reply.updateParent(replyParent);
        }

        replyRepository.save(reply);
    }


    @Override
    public void updateReply(UpdateReplyRequest updateReplyRequest) {
        Reply findReply = replyRepository.findById(updateReplyRequest.getReplyId()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다"));
        findReply.updateReply(updateReplyRequest.getContent());
    }


    @Override
    public void deleteReply(Long num) { // 게시글 넘
        replyRepository.deleteById(num);
    }

    @Override
    public int getReplyCnt(Long num) { // boardNum

        return 0;
    }
}
