package com.study.studyproject.reply.service;

import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.Reply;
import com.study.studyproject.global.exception.ex.NotFoundException;
import com.study.studyproject.global.exception.ex.UserNotFoundException;
import com.study.studyproject.global.jwt.JwtUtil;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.reply.dto.ReplyRequestDto;
import com.study.studyproject.reply.dto.UpdateReplyRequest;
import com.study.studyproject.reply.repository.ReplyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {


    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final JwtUtil jwtUtil;



    @Override
    public void insert(Long idFromToken, ReplyRequestDto replyRequestDto) {
        Member member = memberRepository.findById(idFromToken).orElseThrow(() -> new NotFoundException("사용자 없음"));

        Board board = boardRepository.findById(replyRequestDto.getBoardId())
                .orElseThrow(() -> new NotFoundException("게시글이 없습니다."));

        ;
        Reply reply = Reply.toEntity(replyRequestDto, board, member);

        if (replyRequestDto.getParentId() != null) { // 대댓글인 경우
            Reply replyParent = replyRepository.findById(replyRequestDto.getParentId())
                    .orElseThrow(() -> new NotFoundException(("댓글을 찾을 수 없습니다.")));
            reply.updateParent(replyParent);
        }


        reply.updateWriter(member);
        reply.UpdateBoard(board);

        replyRepository.save(reply);
    }


    @Override
    public void updateReply(UpdateReplyRequest updateReplyRequest) {
        Reply findReply = replyRepository.findById(updateReplyRequest.getReplyId()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다"));
        findReply.updateReply(updateReplyRequest.getContent());
    }


    @Override
    public void deleteReply(Long num) { //댓글 num
        Reply reply = replyRepository.findCommentByIdWithParent(num)
                .orElseThrow(() -> new NotFoundException("댓글을 찾기 못했습니다."));

        int size = reply.getChildren().size();
        System.out.println("size = " + size);
        if (size != 0) { //자식이 있는 상태
            reply.ChangeIsDeleted(true);
        } else { //삭제 가능한 조상 댓글
            replyRepository.delete(getDelete(reply));
        }
    }

    private Reply getDelete(Reply reply) {
        Reply parent = reply.getParent();
        System.out.println("parent = " + parent);
        System.out.println("삭제");
        if (parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted()) {
            return getDelete(parent);
        }
        return reply;
    }


}
