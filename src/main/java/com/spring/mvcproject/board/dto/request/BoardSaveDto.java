package com.spring.mvcproject.board.dto.request;

import com.spring.mvcproject.board.entity.Board;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

// DTO에서는 클라이언트의 데이터를 객체 세팅하기 위해
// ALL 생성자나 Setter를 반드시 구현해놔야 함.
// 실무적 측면 - Setter 구현은 불변성을 해칠 수 있으므로 신중하게 선택

@ToString
@Getter     // Getter는 조회만 하므로 많이 만들어도 상관이 없음!
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BoardSaveDto {

    // NotBlank는 빈 문자열만 방지 ""
    // NotNull은 null값만 방지
    // NotEmpty는 둘 다 방지

    // NotBlank는 빈문자열만 방지 ""
    // NotNull은 null값만 방지
    // NotEmpty는 둘다 방지


    @NotEmpty(message = "제목은 필수입니다.")
    @Size(min = 3, max = 15, message = "글자 수는 3~15자 사이여야 합니다.")
    private String title;

    @NotEmpty(message = "내용은 필수입니다.")
    private String content;


    // 이 DTO를 데이터베이스에 저장하기 위해 Entity로 변환하는 메서드
    public Board toEntity() {
        Board b = new Board();
        b.setTitle(this.title);
        b.setContent(this.content);
        b.setViewCount(0);
        b.setRegDateTime(LocalDateTime.now());
        return b;
    }


}
