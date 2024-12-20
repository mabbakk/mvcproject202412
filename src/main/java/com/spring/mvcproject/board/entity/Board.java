package com.spring.mvcproject.board.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    private Long id; // 글번호
    private String title; //제목
    private String content; //내용
    private int viewCount; //조회수
    private LocalDateTime regDateTime; //작성일시

    // 정적 팩토리 메서드: 객체를 생성할 때 자동으로 들어가는
    // 필드를 제외하고 객체를 빠르게 생성해주는 메서드
    public static Board of(Long id, String title, String content) {
        Board b = new Board();
        b.setId(id);
        b.setTitle(title);
        b.setContent(content);
        b.setViewCount(0);
        b.setRegDateTime(LocalDateTime.now());
        return b;
    }

}