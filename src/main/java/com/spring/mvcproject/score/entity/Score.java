package com.spring.mvcproject.score.entity;


import lombok.*;


@Setter @Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

// 학생 한 명의 성적 정보를 저장
public class Score {

    private Long id;      // 학번
    private String name;  // 이름
    private int kor, eng, math;   // 국영수 점수
}
