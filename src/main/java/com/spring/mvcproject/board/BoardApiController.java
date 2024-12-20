package com.spring.mvcproject.board;

import com.spring.mvcproject.board.dto.request.BoardSaveDto;
import com.spring.mvcproject.board.dto.response.BoardListDto;
import com.spring.mvcproject.board.entity.Board;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/api/v1/boards")
public class BoardApiController {

    private Map<Long, Board> boardStore = new HashMap<>();

    private long nextId = 1;

    public BoardApiController() {
        Board b1 = Board.of(nextId++, "꿀잼게시물", "개노잼이야 사실");
        b1.setRegDateTime(LocalDateTime.of(2023, 11, 11, 0,0,0));
        Board b2 = Board.of(nextId++, "앙영하긔", "긔긔요미미미ㅣ");
        b2.setRegDateTime(LocalDateTime.of(2024, 3, 15, 0,0,0));
        Board b3 = Board.of(nextId++, "이마트 갈때...", "홈플러스 쿠폰써도 되나요");

        boardStore.put(b1.getId(), b1);
        boardStore.put(b2.getId(), b2);
        boardStore.put(b3.getId(), b3);
    }


    // 게시물 목록조회 GET
    @GetMapping
    public ResponseEntity<?> boardList() {
        // 게시물 목록은 최신글이 가장 위에 있어야 함
        List<BoardListDto> collect = new ArrayList<>(boardStore.values())
                .stream()
                .sorted(comparing(Board::getRegDateTime).reversed())
                .map(b -> new BoardListDto(b))
                .collect(toList());

        return ResponseEntity.ok().body(collect)
                ;
    }


    // 게시물 삭제 DELETE
    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable Long id) {
        Board removed = boardStore.remove(id);
        if (removed == null) {
            return "해당 id는 존재하지 않습니다: id = " + id;
        }
        return "게시물 삭제 성공! - " + removed;
    }

//    // 게시물 등록 POST   [ 아래 리팩토링 전!!!! ]
//    @PostMapping
//    public String createBoard(
//            @RequestBody Board board
//    ) {
//        board.setId(nextId++);  // 사용자에게서는 제목과 내용만 받고 아이디는 우리가 정해줘야함!
//        board.setRegDateTime(LocalDateTime.now());  // 시간도 우리가!
//
//        System.out.println("board = " + board);
//        boardStore.put(board.getId(), board);
//
//        return "게시물 등록 성공! - "+ board;
//    }

    // 게시물 등록 POST      //  [ 리팩토링 후!!!!!  ]
    @PostMapping
    public ResponseEntity<?> createBoard(
            @RequestBody @Valid BoardSaveDto dto,
            BindingResult bindingResult
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

        System.out.println("dto = " + dto);

        Board board = dto.toEntity();
        board.setId(nextId++);

        System.out.println("board = " + board);
        boardStore.put(board.getId(), board);

        return ResponseEntity.ok().body("게시물 등록 성공! - "+ board);

    }

    // 게시물 상세 조회 요청
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detailBoard(@PathVariable Long id) {
        // Map에서 단일 조회 수행
        Board targetId = boardStore.get(id);
        return ResponseEntity
                .ok()
                .body("조회 성공! id = " + id);
    }


}