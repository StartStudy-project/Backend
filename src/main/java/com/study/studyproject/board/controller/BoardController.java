
package com.study.studyproject.board.controller;

import com.study.studyproject.board.dto.BoardChangeRecruitRequestDto;
import com.study.studyproject.board.dto.BoardOneResponseDto;
import com.study.studyproject.board.dto.BoardReUpdateRequestDto;
import com.study.studyproject.board.dto.BoardWriteRequestDto;
import com.study.studyproject.board.service.BoardService;
import com.study.studyproject.entity.Role;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.global.auth.UserDetailsImpl;
import com.study.studyproject.global.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/")
@Tag(name = "게시판 API", description = "게시판 Document")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final JwtUtil jwtUtil;

    //글쓰기 수정
    @PatchMapping("member/updateWrite")
    @Operation(summary = "글쓰기 수정", description = "글쓰기 수정 기능")
    public ResponseEntity<GlobalResultDto> updateWriting(@RequestBody @Validated  BoardReUpdateRequestDto boardReUpdateRequestDto) {
        return ResponseEntity.ok(boardService.updateWrite(boardReUpdateRequestDto));

    }

    @PostMapping("member/writing")
    @Operation(summary = "글쓰기 작성", description = "글쓰기 작성")
    public ResponseEntity<GlobalResultDto> writing(@RequestBody @Validated  BoardWriteRequestDto boardWriteRequestDto,@RequestHeader("Access_Token") String token)  {
        Long idFromToken = jwtUtil.getIdFromToken(jwtUtil.resolveToken(token));
        return ResponseEntity.ok(boardService.boardSave(boardWriteRequestDto, idFromToken));

    }

    @Operation(summary = "모집구분 변경", description = "모집구분 변경")
    @PatchMapping("/member/changeRecruit")
    public ResponseEntity<GlobalResultDto> changeRecruit(@RequestBody @Validated BoardChangeRecruitRequestDto boardChangeRecruitRequestDto) {
        return ResponseEntity.ok(boardService.changeRecruit(boardChangeRecruitRequestDto));
    }


    //삭제
    @DeleteMapping("member/{boardId}")
    @Operation(summary = "게시글 삭제 ", description = "해당 게시글 삭제")
    public ResponseEntity<GlobalResultDto> deleteBoard(@PathVariable(name = "boardId") Long boardId, @RequestParam(required = false, defaultValue =  "ROLE_USER") Role role) {
        return ResponseEntity.ok(boardService.boardDeleteOne(boardId,role));
    }


    //글 조회 1개 -
    @GetMapping("/{boardId}")
    @Operation(summary = "게시글 상세", description = "게시글 상세페이지")
    public ResponseEntity<BoardOneResponseDto> getBoard(@Parameter(description = "게시판 ID") @PathVariable(name = "boardId") Long boardId
            ,@RequestHeader(value = "Access_Token",required = false) String token,HttpServletRequest request,HttpServletResponse response) {

        String resolveToken = jwtUtil.resolveToken(token);
        BoardOneResponseDto boardOneResponseDto = boardService.boardOne(boardId, resolveToken,request,response);
        return ResponseEntity.ok(boardOneResponseDto);

    }



}
