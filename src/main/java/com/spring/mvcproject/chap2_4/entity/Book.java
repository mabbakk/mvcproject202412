package com.spring.mvcproject.chap2_4.entity;


import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor  // 모든 생성자
@NoArgsConstructor  // 기본 생성자

public class Book {  // 하나의 정보만 담고 있는 클래스일 때 @Coponent로 스프링에게 맡긴다.

    private Long id;        // 아이디
    private String title;   // 도서 제목
    private String author;  // 도서 저자
    private int price;   // 가격

    public void updateBook(String title, String author, int price) {

    }
}
