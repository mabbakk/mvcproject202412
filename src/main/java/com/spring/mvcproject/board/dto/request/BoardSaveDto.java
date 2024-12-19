package com.spring.mvcproject.board.dto.request;

public class BoardSaveDto {

    // 필수값 검증 (아무것도 쓰지 않고 넘어오지 못하게)
    private String title;
    private String content;
}