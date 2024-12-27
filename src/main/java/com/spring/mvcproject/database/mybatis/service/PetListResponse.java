package com.spring.mvcproject.database.mybatis.dto.response;

import lombok.*;

import java.util.List;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetListResponse {
    private int totalCount;
    private List<PetResponse> petList;
}