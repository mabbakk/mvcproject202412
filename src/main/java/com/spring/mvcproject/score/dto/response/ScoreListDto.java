package com.spring.mvcproject.score.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.mvcproject.score.entity.Score;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ScoreListDto {
    private Long id; // 학번
    private String maskingName; // 가려진 이름

    @JsonProperty("sum")
    private int total; // 총점

    @JsonProperty("avg")
    private double average; // 평균 소수점 2자리까지만
    private int rank; // 석차


    // Entity : 데이터베이스에 저장되는 모든 데이터 스펙
    // 생성자를 통해 엔터티 데이터를 받아 처리
    public ScoreListDto(Score score) {
        this.id = score.getId();
        this.total = score.getKor() + score.getMath() + score.getEng();
        this.average = Math.round((this.total / 3.0) * 100) / 100.0;
        this.maskingName = makeMaskingName(score.getName());

        // 3.6789 -> 3.679
        // 3.6789 * 1000 => 3678.9 => 반올림 => 3679
        // 3679 / 1000 => 3.679
    }

    /**
     * 원본 이름을 첫글자 마지막글자만 보여주고 나머지 * 처리
     * 만약 이름이 2글자라면 첫글자만 보여주고 두번째글자는 * 처리
     * @param originalName - 마스킹이 안된 원본 이름
     * @return - 마스킹처리된 이름
     */
    private String makeMaskingName(String originalName) {
        // 이름이 2글자인 사람 처리
        if (originalName.length() <= 2) {
            return originalName.charAt(0) + "*";
        }
        // 나머지 경우
        char firstLetter = originalName.charAt(0); // 첫글자 추출
        char lastLetter = originalName.charAt(originalName.length() - 1);// 끝글자 추출

        String maskingName = String.valueOf(firstLetter);
        for (int i = 1; i < originalName.length() - 1; i++) {
            maskingName += "*";
        }
        maskingName += lastLetter;
        return maskingName;
    }
}