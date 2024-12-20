package com.study.studyproject.board.dto;

import com.study.studyproject.board.domain.Category;
import com.study.studyproject.board.domain.ConnectionType;
import com.study.studyproject.board.domain.OfflineLocation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardReUpdateRequestDto {
    //모집구분
    @Schema(description = "boardId", defaultValue = "1")
    @NotNull(message = "게시글 번호를 입력해주세요")
    Long boardId;

    @Schema(description = "변경할 내용", defaultValue = "수정 내용")
    @NotBlank(message = "내용을 입력해주세요")
    String content;

    @Schema(description = "카테고리", defaultValue = "CS")
    @NotNull(message = "카테고리를 입력해주세요")
    Category category;

    @Schema(description = "제목", defaultValue = "제목")
    @NotBlank(message = "제목을 입력해주세요")
    String title;

    @Schema(description = "타입", defaultValue = "오프라인")
    ConnectionType connectionType;

    OfflineLocation offlineLocation;

    @Builder
    public BoardReUpdateRequestDto(Long boardId, String content, Category category, String title, ConnectionType connectionType, OfflineLocation offlineLocation) {
        this.boardId = boardId;
        this.content = content;
        this.category = category;
        this.title = title;
        this.connectionType = connectionType;
        this.offlineLocation = offlineLocation;
    }
}
