package com.spring.mvcproject.score.api;

import com.spring.mvcproject.score.dto.request.ScoreCreateDto;
import com.spring.mvcproject.score.dto.response.ScoreDetailDto;
import com.spring.mvcproject.score.dto.response.ScoreListDto;
import com.spring.mvcproject.score.entity.Score;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

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
    public ResponseEntity<List<ScoreListDto>> scoreList(
            @RequestParam(required = false, defaultValue = "id") String sort
    ) {

        System.out.println("정렬기준: " + sort);

        /*
        // 데이터베이스에서 성적 정보를 모두 꺼내옴
        List<Score> originalScores = new ArrayList<>(scoreStore.values());
        // 새로운 DTO리스트를 생성
        List<ScoreListDto> responseList = new ArrayList<>();
        for (Score score : originalScores) {
            ScoreListDto dto = new ScoreListDto(score);
            responseList.add(dto);
        }
        */

        List<ScoreListDto> responseList = new ArrayList<>(scoreStore.values())
                .stream()
                .map(score -> new ScoreListDto(score))
                .collect(Collectors.toList());

        // 석차 구하기
        calculateListRank(responseList);

        // 정렬 파라미터 처리
        responseList.sort(getScoreComparator(sort));

        return ResponseEntity
                .ok()
                .body(responseList);
    }

    // 성적 상세조회 요청
    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        // 데이터베이스(Map)에서 단일 조회 수행
        Score targetStudent = scoreStore.get(id);
        if (targetStudent == null) {
            return ResponseEntity
                    .status(404)
                    .body("해당 정보를 찾을 수 없습니다: id - " + id);
        }

        // 석차와 총 학생수를 구하기 위해 학생 목록을 가져옴
        List<Score> scoreList = new ArrayList<>(scoreStore.values());

        scoreList.sort(Comparator.comparing((Score s) -> s.getKor() + s.getEng() + s.getMath()).reversed());

        int rank = 1;
        for (Score s : scoreList) { // 전체 학생을 순회하면서
            if (s.getId().equals(targetStudent.getId())) { // 현재 발견된 학생을 찾으면 스톱
                break;
            }
            rank++;
        }

        // Score엔터티를 ScoreDetailDto로 변환
        ScoreDetailDto responseDto = new ScoreDetailDto(targetStudent, scoreList.size());
        responseDto.setRank(rank);

        return ResponseEntity
                .ok()
                .body(responseDto);
    }


    // 전체조회시 석차 구하기
    private static void calculateListRank(List<ScoreListDto> responseList) {
        // 석차 구하기
        // 총점 내림차로 정렬
        responseList.sort(comparing(ScoreListDto::getAverage).reversed());

        int currentRank = 1; // 현재 등수
        for (ScoreListDto dto : responseList) {
            dto.setRank(currentRank++);
        }
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
        Score score = dto.toEntity();
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
    private Comparator<ScoreListDto> getScoreComparator(String sort) {
        Comparator<ScoreListDto> comparing = null;
        switch (sort) {
            case "id":
                comparing = comparing(ScoreListDto::getId);
                break;
            case "name":
                comparing = comparing(ScoreListDto::getMaskingName);
                break;
            case "average":
                comparing = comparing(ScoreListDto::getAverage).reversed();
        }
        return comparing;
    }

}
