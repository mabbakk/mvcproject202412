package com.spring.mvcproject.score.repository;

import com.spring.mvcproject.score.entity.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

// DB연동 클래스
@Repository
@RequiredArgsConstructor
public class ScoreJdbcRepository implements ScoreRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Score> findAll(String sort) {
        return jdbcTemplate.query("""
                 SELECT * FROM scores
                """,
                new BeanPropertyRowMapper<>(Score.class));
    }

    @Override
    public Score findOne(Long id) {
        return jdbcTemplate.queryForObject("""
                SELECT * FROM scores
                WHERE id = ?
                """,
                new BeanPropertyRowMapper<>(Score.class),
                id
        );
    }

    @Override
    public void save(Score score) {
        jdbcTemplate.update("""
                INSERT INTO scores 
                    (name, kor, eng, math)
                VALUES 
                    (?, ?, ?, ?)
                """, score.getName(), score.getKor(), score.getEng(), score.getMath());
    }

    @Override
    public boolean deleteById(Long id) {
        int result = jdbcTemplate.update("""
                DELETE FROM scores 
                WHERE id = ?
                """, id);
        return result > 0;
    }
}
