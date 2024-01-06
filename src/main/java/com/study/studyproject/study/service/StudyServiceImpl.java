package com.study.studyproject.study.service;

import org.springframework.stereotype.Service;

@Service
public class StudyServiceImpl implements  StudyService{


    @Override
    public void studyMainService(String type, int seq, int page) {
        String getType = (type != null) ? type : "ALL";


    }
}
