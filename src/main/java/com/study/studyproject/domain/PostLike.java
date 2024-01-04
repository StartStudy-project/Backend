package com.study.studyproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
public class PostLike extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private Long id;



}
