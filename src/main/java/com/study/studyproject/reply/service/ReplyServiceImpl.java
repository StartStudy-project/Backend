package com.study.studyproject.reply.service;

import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Reply;
import com.study.studyproject.global.exception.ex.ErrorCode;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.reply.dto.ReplyRequestDto;
import com.study.studyproject.reply.dto.UpdateReplyRequest;
import com.study.studyproject.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.study.studyproject.global.exception.ex.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {


    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public void insert(Member member, ReplyRequestDto replyRequestDto) {
        Board board = boardRepository.findById(replyRequestDto.getBoardId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOARD));

        Reply reply = Reply.toEntity(replyRequestDto, board, member);

        if (isReplyParent(replyRequestDto)) { // 대댓글인 경우
            Reply replyParent = replyRepository.findById(replyRequestDto.getParentId())
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_REPLY));
            reply.updateParent(replyParent);
        }


        reply.updateWriter(member);
        reply.UpdateBoard(board);

        replyRepository.save(reply);
    }

    private static boolean isReplyParent(ReplyRequestDto replyRequestDto) {
        return replyRequestDto.getParentId() != null;
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
        if (hasChildrenReplies(reply)) { //자식이 있는 상태
            reply.ChangeIsDeleted(true);
        } else { //삭제 가능한 조상 댓글
            replyRepository.delete(getDelete(reply));
        }

    }

    private static boolean hasChildrenReplies(Reply reply) {
        return reply.getChildren().size() != 0;
    }

    private Reply getDelete(Reply reply) {
        Reply parent = reply.getParent();
        if (parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted()) {
            return getDelete(parent);
        }
        return reply;
    }


}
