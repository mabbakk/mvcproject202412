package com.spring.mvcproject.chap1_3;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// Controller : 클라이언트의 요청을 받아 로직을 수행하는 역할
//@Component 보다 더 기능이 많은
@Controller // DispatcherServlet이 이 객체를 탐색해서 연결해줌
@RequestMapping("/chap01")
public class HelloController {

    @RequestMapping("/hello")   // /hello 자리에 내가 어떤 요청을 받을 것인지 해당 링크를 적어라.
    @ResponseBody  // JSON 응답!
    public String hello() {   // 여기서 요청을 처리할 메서드 작성!
        System.out.println("hello ~~ spring mvc world!!");
        return "메롱메롱 안녕안녕";
    }

    @RequestMapping("/bye")
    public String bye() {
        System.out.println("bye ~~ spring mvc world!!");
        return "";
    }
}
