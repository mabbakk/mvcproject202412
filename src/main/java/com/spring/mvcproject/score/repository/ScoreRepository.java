package com.spring.mvcproject.score.repository;

import com.spring.mvcproject.score.entity.Score;

import java.util.List;

// 역할: 실질적인 데이터 CRUD를 수행
//   - 실제 데이터베이스에 접근해서 하는 일들을 전담
public interface ScoreRepository {

    // 전체 조회 기능 명세
    List<Score> findAll(String sort);

    // 개별 조회 기능 명세
    Score findOne(Long id);

    // 저장 기능 명세
    void save(Score score);

    // 삭제 기능 명세
    boolean deleteById(Long id);
}
