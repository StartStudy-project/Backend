package com.study.studyproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class reply extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long id;
    private String content;


}
