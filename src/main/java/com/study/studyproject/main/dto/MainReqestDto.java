package com.study.studyproject.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "main request DTO")
public class MainReqestDto {

    @Schema(description = "type", example = "ETC")
    private String type;

    @Schema(description = "순서", example = "최신순")
    private String order;

}
