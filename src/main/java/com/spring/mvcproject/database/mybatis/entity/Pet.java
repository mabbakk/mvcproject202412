package com.spring.mvcproject.database.mybatis.entity;

/*   회사에서 이렇게 DB를 노출시키면 절대 안 됨!! 해킹 당함
CREATE TABLE PRACTICE.TBL_PET (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY ,
    PET_NAME VARCHAR(50),
    PET_AGE INT,
    INJECTION BOOLEAN
);
 */


import lombok.*;

@Setter @Getter @ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {
    private Long id;
    private String petName;
    private int patAge;
    private boolean injection;

}
