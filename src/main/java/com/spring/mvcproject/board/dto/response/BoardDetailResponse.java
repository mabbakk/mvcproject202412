package com.spring.mvcproject.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.mvcproject.board.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

// 클라이언트가 상세조회 페이지에 그려넣을 데이터
@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailResponse {
    private Long bno;

    @JsonProperty("board_title")
    private String title;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    // 정적 팩토리 메서드: 빠르게 이 객체를 생성
    public static BoardDetailResponse from(Board board) {
        BoardDetailResponse response = new BoardDetailResponse();
        response.bno = board.getId();
        response.title = board.getTitle();
        response.content = board.getContent();
        response.date = board.getRegDateTime();
        return response;
    }

}
