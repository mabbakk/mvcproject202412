package com.spring.mvcproject.score.repository;

import com.spring.mvcproject.score.dto.response.ScoreListDto;
import com.spring.mvcproject.score.entity.Score;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

import static java.util.Comparator.comparing;

// 역할: 성적정보를 메모리에서 관리하는 역할
//@Component  // 스프링에 등록!
@Repository  // 위에 Component 대신에 쓸 수 있음! Component 포함하고 있음.
public class ScoreMemoryRepo implements ScoreRepository {

    private Map<Long, Score> scoreStore = new HashMap<>();

    private Long nextId = 1L;

    public ScoreMemoryRepo() {
        Score s1 = new Score(nextId++, "김말복", 100, 88, 75);
        Score s2 = new Score(nextId++, "박수포자", 55, 95, 15);
        Score s3 = new Score(nextId++, "김마이클", 4, 100, 40);
        Score s4 = new Score(nextId++, "세종대왕", 100, 0, 90);

        scoreStore.put(s1.getId(), s1);
        scoreStore.put(s2.getId(), s2);
        scoreStore.put(s3.getId(), s3);
        scoreStore.put(s4.getId(), s4);
    }

    @Override
    public List<Score> findAll(String sort) {
        // 저장된 성적 정보를 모두 읽어옴
        List<Score> scoreList = new ArrayList<>(scoreStore.values());
        return scoreList;
    }

    @Override
    public Score findOne(Long id) {
        return scoreStore.get(id);
    }

    @Override
    public void save(Score score) {
        score.setId(nextId++);
        scoreStore.put(score.getId(), score);
    }

    @Override
    public boolean deleteById(Long id) {
        Score removed = scoreStore.remove(id);
        return removed != null;
    }


}
