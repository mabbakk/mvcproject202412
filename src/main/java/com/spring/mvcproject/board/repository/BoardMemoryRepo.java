package com.spring.mvcproject.board.repository;


import com.spring.mvcproject.board.entity.Board;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Repository
public class BoardMemoryRepo implements BoardRepository {

    private Map<Long, Board> boardStore = new HashMap<>();

    private long nextId = 1;

    public BoardMemoryRepo() {
        Board b1 = Board.of(nextId++, "꿀잼게시물", "개노잼이야 사실");
        b1.setRegDateTime(LocalDateTime.of(2023, 11, 11, 0,0,0));
        Board b2 = Board.of(nextId++, "앙영하긔", "긔긔요미미미ㅣ");
        b2.setRegDateTime(LocalDateTime.of(2024, 3, 15, 0,0,0));
        Board b3 = Board.of(nextId++, "이마트 갈때...", "홈플러스 쿠폰써도 되나요");

        boardStore.put(b1.getId(), b1);
        boardStore.put(b2.getId(), b2);
        boardStore.put(b3.getId(), b3);
    }


    @Override
    public List<Board> findAll() {
        return List.of();
    }

    @Override
    public boolean save(Board board) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Board findOne(Long id) {
        return null;
    }
}
