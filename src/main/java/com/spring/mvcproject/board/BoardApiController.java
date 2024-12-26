package com.spring.mvcproject.board.api;

import com.spring.mvcproject.board.dto.request.BoardSaveDto;
import com.spring.mvcproject.board.dto.response.BoardDetailResponse;
import com.spring.mvcproject.board.dto.response.BoardListDto;
import com.spring.mvcproject.board.entity.Board;
import com.spring.mvcproject.board.repository.BoardRepository;
import com.spring.mvcproject.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    // 게시물 목록조회 GET
    @GetMapping
    public ResponseEntity<?> boardList() {
        return ResponseEntity.ok().body(boardService.getList());
    }

    // 게시물 삭제 DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
        boolean flag = boardService.delete(id);
        if (!flag) {
            return ResponseEntity
                    .badRequest()
                    .body("해당 id는 존재하지 않습니다: id = " + id);
        }
        return ResponseEntity.ok().body("게시물 삭제 성공! ");
    }

    // 게시물 등록 POST
    @PostMapping
    public ResponseEntity<?> createBoard(
            @RequestBody @Valid BoardSaveDto dto
            , BindingResult bindingResult
    ) {
        // 입력값 검증 응답 처리
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errorMap.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity
                    .badRequest()
                    .body(errorMap)
                    ;
        }

        boardService.create(dto);

        return ResponseEntity.ok().body("게시물 등록 성공! ");

    }

    // 게시물 상세조회 요청처리
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        // 특정 게시물 찾아오기
        BoardDetailResponse foundBoard = boardService.getArticle(id);

        // 게시물이 없을 경우
        if (foundBoard == null) {
            return ResponseEntity
                    .badRequest() // 400
                    .body(id + "번 게시물은 존재하지 않습니다.");
        }

        // 게시물 원본데이터를 클라이언트 스펙에 맞게 변환
        return ResponseEntity
                .ok()
                .body(foundBoard);
    }

}