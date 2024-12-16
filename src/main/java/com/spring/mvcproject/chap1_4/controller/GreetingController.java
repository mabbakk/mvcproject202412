package com.spring.mvcproject.chap1_4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {

    @RequestMapping("/greeting")
    @ResponseBody
    public String greeting(String name) {
        // 여기서는 일단  비즈니스 로직... 이 생략됨!

        // 결과 반환
        return "안녕하세요! " + name + "님";
    }
}
