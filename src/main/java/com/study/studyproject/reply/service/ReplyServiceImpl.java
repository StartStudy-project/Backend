package com.study.studyproject.reply.service;

import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.domain.Board;
import com.study.studyproject.domain.Member;
import com.study.studyproject.domain.Reply;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.reply.dto.ReplyRequestDto;
import com.study.studyproject.reply.dto.UpdateReplyRequest;
import com.study.studyproject.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.study.studyproject.global.exception.ex.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {


    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void insert(Long memberId, ReplyRequestDto replyRequestDto) {
        Board board = boardRepository.findById(replyRequestDto.getBoardId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOARD));

        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));

        Reply reply = Reply.toEntity(replyRequestDto, board, member);

        if (replyRequestDto.isReplyParent()) { // 대댓글인 경우
            Reply replyParent = replyRepository.findById(replyRequestDto.getParentId())
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_REPLY));
            reply.updateParent(replyParent);
        }

//문제
        reply.UpdateBoard(board);
        reply.updateWriter(member);


        replyRepository.save(reply);
    }




    @Override
    public void updateReply(UpdateReplyRequest updateReplyRequest) {
        Reply findReply = replyRepository.findById(updateReplyRequest.getReplyId()).orElseThrow(() -> new NotFoundException(NOT_FOUND_REPLY));
        findReply.updateReply(updateReplyRequest.getContent());
    }


    @Override
    public void deleteReply(Long num) { //댓글 num
        Reply reply = replyRepository.findCommentByIdWithParent(num)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_REPLY));
        if (reply.hasChildrenReplies()) { //자식이 있는 상태
            reply.ChangeIsDeleted(true);
        } else { //삭제 가능한 조상 댓글
            replyRepository.delete(getDelete(reply));
        }

    }


    private Reply getDelete(Reply reply) {
        Reply parent = reply.getParent();
        if (isDeleteReply(parent)) {
            return getDelete(parent);
        }
        return reply;
    }

    private static boolean isDeleteReply(Reply parent) {
        return parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted();
    }


}
