package com.spring.mvcproject.score.api;

import com.spring.mvcproject.score.dto.request.ScoreCreateDto;
import com.spring.mvcproject.score.entity.Score;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController // JSON응답
@RequestMapping("/api/v1/scores")
public class ScoreApiController {

    private Map<Long, Score> scoreStore = new HashMap<>();

    private Long nextId = 1L;

    public ScoreApiController() {
        Score s1 = new Score(nextId++, "김말복", 100, 88, 75);
        Score s2 = new Score(nextId++, "박수포자", 55, 95, 15);
        Score s3 = new Score(nextId++, "김마이클", 4, 100, 40);
        Score s4 = new Score(nextId++, "세종대왕", 100, 0, 90);

        scoreStore.put(s1.getId(), s1);
        scoreStore.put(s2.getId(), s2);
        scoreStore.put(s3.getId(), s3);
        scoreStore.put(s4.getId(), s4);
    }

    // 전체 성적정보 조회 (정렬 파라미터를 읽어야 함)
    // /api/v1/scores?sort=name
    @GetMapping
    public List<Score> scoreList(
            @RequestParam(required = false, defaultValue = "id") String sort
    ) {

        System.out.println("정렬기준: " + sort);

        return new ArrayList<>(scoreStore.values())
                .stream()
                .sorted(getScoreComparator(sort))
                .collect(Collectors.toList())
                ;
    }

    // 성적 정보 생성 요청 처리
    @PostMapping
    public ResponseEntity<?> createScore(
            // 클라이언트가 성적정보를 JSON으로 보냈다
            @RequestBody @Valid ScoreCreateDto dto
            // 입력값 검증 결과를 가진 객체
            , BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) { // 입력값 검증에서 에러가 발생했다면
            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errorMap.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity
                    .badRequest()
                    .body(errorMap)
                    ;
        }

        // ScoreCreateDto를 Score로 변환하는 작업
        Score score = new Score(dto);
        score.setId(nextId++);

        scoreStore.put(score.getId(), score);
        return ResponseEntity
                .ok()
                .body("성적 정보 생성 완료! " + score);

    }

    // 성적 정보 삭제요청 처리
    @DeleteMapping("/{id}")
    public String deleteScore(
            @PathVariable Long id
    ) {
        scoreStore.remove(id);
        return "성적 정보 삭제 성공! - id: " + id;
    }





    // 정렬 처리를 위한 정렬기 생성 유틸 메서드
    private Comparator<Score> getScoreComparator(String sort) {
        Comparator<Score> comparing = null;
        switch (sort) {
            case "id":
                comparing = Comparator.comparing(Score::getId);
                break;
            case "name":
                comparing = Comparator.comparing(Score::getName);
                break;
        }
        return comparing;
    }

}
