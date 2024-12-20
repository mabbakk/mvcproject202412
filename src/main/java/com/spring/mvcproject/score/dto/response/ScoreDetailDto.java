package com.spring.mvcproject.score.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.mvcproject.score.entity.Score;
import lombok.*;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDetailDto {

    private Long id;
    @JsonProperty("fullName")
    private String name;
    private int kor, eng, math;
    private int total;
    private double average;

    @Setter
    private int rank;
    private int totalCount; // 총 학생 수

    public ScoreDetailDto(Score s, int totalCount) {
        this.id = s.getId();
        this.kor = s.getKor();
        this.eng = s.getEng();
        this.math = s.getMath();
        this.total = kor + eng + math;
        this.average = Math.round(total / 3.0 * 100) / 100.0;
        this.totalCount = totalCount;
    }
}
