package com.study.studyproject.study.service;

import com.study.studyproject.entity.Board;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyServiceImpl implements  StudyService{

    /**
     *     ETC("기타"),
     *     ALL("전체"),
     *     CS("CS"),
     *     CODE_TEST("코테"),
     *     PROJECT("프로젝트");
     */

    BoardRes

    @Override
    public void selectBoardList(String type, String order, int page) {
        String getType = (type != null) ? type : "ALL";

        if (getType.equals("ALL")) {



        } else{

        }

    }
}
