package com.study.studyproject.list.dto;

import com.study.studyproject.entity.Category;
import lombok.Data;

@Data
public class MainRequest {

    Category category;

    int order; // 0 -> 최신순


}
