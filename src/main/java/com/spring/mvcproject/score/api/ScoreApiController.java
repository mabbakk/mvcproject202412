package com.spring.mvcproject.score.api;

import com.spring.mvcproject.score.dto.request.ScoreCreateDto;
import com.spring.mvcproject.score.dto.response.ScoreDetailDto;
import com.spring.mvcproject.score.dto.response.ScoreListDto;
import com.spring.mvcproject.score.entity.Score;
import com.spring.mvcproject.score.service.ScoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 컨트롤러는 서비스에게 의존
    private ScoreService scoreService;

    @Autowired
    public ScoreApiController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    // 전체 성적정보 조회 (정렬 파라미터를 읽어야 함)
    // /api/v1/scores?sort=name   : GET
    @GetMapping
    public ResponseEntity<List<ScoreListDto>> scoreList(
            @RequestParam(required = false, defaultValue = "id") String sort
    ) {
        // 서비스에서 비즈니스 로직 처리 위임
        List<ScoreListDto> responseList = scoreService.getList(sort);

        return ResponseEntity
                .ok()
                .body(responseList);
    }

    // 성적 상세조회 요청
    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {

        try {
            // 서비스에게 단일 조회 관련처리를 위임
            ScoreDetailDto responseDto = scoreService.getDetail(id);

            return ResponseEntity
                    .ok()
                    .body(responseDto);
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
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

        // 서비스에게 생성 로직 처리를 위임
        Score score = scoreService.create(dto);

        return ResponseEntity
                .ok()
                .body("성적 정보 생성 완료! " + score);

    }

    // 성적 정보 삭제요청 처리
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScore(
            @PathVariable Long id
    ) {

        try {
            scoreService.remove(id);
            return ResponseEntity
                    .ok()
                    .body("성적 정보 삭제 성공! - id: " + id);
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

    }

}
