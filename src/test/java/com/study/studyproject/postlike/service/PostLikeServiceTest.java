package com.study.studyproject.postlike.service;

import com.study.studyproject.board.repository.BoardRepository;
import com.study.studyproject.entity.Board;
import com.study.studyproject.entity.Category;
import com.study.studyproject.entity.Member;
import com.study.studyproject.entity.PostLike;
import com.study.studyproject.global.GlobalResultDto;
import com.study.studyproject.member.repository.MemberRepository;
import com.study.studyproject.postlike.repository.PostLikeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static com.study.studyproject.entity.Category.CS;
import static com.study.studyproject.entity.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostLikeServiceTest {

    @Autowired
    PostLikeService postLikeService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Test
    @DisplayName("관심글을 추가한다.")
    void postLikeSave() throws Exception {
        //given
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        Board boardCreate = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(boardCreate);

        //when
        GlobalResultDto globalResultDto = postLikeService.postLikeSave(boardCreate.getId(), member1.getId());

        //then
        assertThat(globalResultDto.getMessage()).isEqualTo("관심글이 추가되었습니다.");

    }


    @Test
    @DisplayName("같은 게시글을 중복으로 관심글 추가할 경우 추가할 수 없다.")
    void postLikeDuplicateSave() throws Exception {
        //given
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        Board boardCreate = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(boardCreate);
        PostLike postLike1 = PostLike.create(member1, boardCreate);
        postLikeRepository.save(postLike1);

        //when
        GlobalResultDto globalResultDto = postLikeService.postLikeSave(boardCreate.getId(), member1.getId());

        //then
        assertThat(globalResultDto.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(globalResultDto.getMessage()).isEqualTo("관심글이 이미 추가하였습니다.");

    }


    @Test
    @DisplayName("사용자가 관심글을 삭제한다.")
    void deletePostLike() throws Exception {
        //given
        Member member1 = createMember("jacom2@naver.com", "1234", "사용자명1", "닉네임1");
        Board boardCreate = createBoard(member1, "제목1", "내용1", "닉네임1", CS);
        memberRepository.save(member1);
        boardRepository.save(boardCreate);
        PostLike postLike1 = PostLike.create(member1, boardCreate);
        postLikeRepository.save(postLike1);

        //when
        GlobalResultDto globalResultDto = postLikeService.postLikeDelete(postLike1.getId());

        //then
        assertThat(globalResultDto.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(globalResultDto.getMessage()).isEqualTo("관심글이 삭제되었습니다.");

    }


    private Member createMember
            (String email, String password, String username, String nickname) {
        {
            return com.study.studyproject.entity.Member.builder()
                    .nickname(nickname)
                    .username(username)
                    .email(email)
                    .password(password)
                    .role(ROLE_USER).build();
        }

    }



    private Board createBoard(
            Member member, String title, String content, String nickname, Category category
    ) {
        return Board.builder()
                .member(member)
                .title(title)
                .content("내용")
                .nickname(nickname)
                .category(category)
                .build();
    }
    

    

}