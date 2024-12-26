package com.spring.mvcproject.board.repository;

import com.spring.mvcproject.board.entity.Board;

import java.util.List;

// 게시물 데이터를 저장, 조회, 수정, 삭제하는 명세서
public interface BoardRepository {

    // 게시물 목록 조회
    List<Board> findAll();

    // 게시물 생성
    boolean save(Board board);

    // 게시물 삭제
    boolean delete(Long id);

    // 게시물 개별 조회
    Board findOne(Long id);
}