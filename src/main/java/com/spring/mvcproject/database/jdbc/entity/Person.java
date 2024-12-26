package com.spring.mvcproject.database.jdbc.entity;

import lombok.*;

// 엔터티 (도메인) 클래스 : 데이터베이스 테이블과 1:1로 매칭되는 자바의 클래스
/*
    CREATE TABLE tbl_person (
        id BIGINT PRIMARY KEY ,
        person_name VARCHAR(100) NOT NULL ,
        age INT DEFAULT 0
    );
 */

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Long id;
    private String personName;
    private int age;
}
