package com.spring.mvcproject.database.mybatis.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.mvcproject.database.mybatis.entity.Pet;
import lombok.*;

import java.time.LocalDate;

// ResponseDto : 데이터 조회시 사용
// 클라이언트가 요구한 스펙대로 데이터를 정제
@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetResponse {

    @JsonProperty("pet_no")
    private Long id;
    private String name;
    private int age;
    private int birth;

    // 엔터티클래스를 자기자신으로 변환하는 정적 팩토리 메서드
    public static PetResponse from(Pet pet) {
        int petAge = pet.getPetAge();
        return PetResponse.builder()
                .id(pet.getId())
                .age(petAge)
                .birth(LocalDate.now().getYear() - petAge + 1)
                .name(pet.getPetName())
                .build();
    }

}