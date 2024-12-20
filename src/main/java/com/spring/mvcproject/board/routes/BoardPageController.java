package com.spring.mvcproject.board.routes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// 게시판 관련 JSP를 열어주는 컨트롤러
@Controller
@RequestMapping("/board")
public class BoardPageController {

    // list-page.jsp를 열어주는 라우팅요청
    @GetMapping("/list")
    public String list() {
        return "board/list-page";
    }

    // write-page.jsp를 열어주는 라우팅요청
    @GetMapping("/write")
    public String write() {
        return "board/write-page";
    }

    // detail-page.jsp를 열어주는 라우팅요청
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id) {
        return "board/detail-page";
    }
}
