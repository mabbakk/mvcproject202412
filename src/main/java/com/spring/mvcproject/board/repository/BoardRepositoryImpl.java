package com.spring.mvcproject.board.repository;

import com.spring.mvcproject.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Board> findAll() {
        return jdbcTemplate.query("""
                SELECT * FROM tbl_board
                ORDER BY reg_date_time DESC
                """, new BeanPropertyRowMapper<>(Board.class));
    }

    @Override
    public boolean save(Board board) {
        return jdbcTemplate.update("""
                INSERT INTO tbl_board 
                    (title, content)
                VALUES 
                    (?, ?)
                """, board.getTitle(), board.getContent()) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update("""
                DELETE FROM tbl_board 
                WHERE id = ?
                """, id) > 0;
    }

    @Override
    public Board findOne(Long id) {
        return jdbcTemplate.queryForObject("""
                SELECT * FROM tbl_board
                WHERE id = ?
                """, new BeanPropertyRowMapper<>(Board.class)
                , id);
    }
}