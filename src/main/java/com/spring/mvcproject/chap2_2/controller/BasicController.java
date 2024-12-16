package com.spring.mvcproject.chap2_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BasicController {

    // GET 요청을 클라이언트가 보냈을 때
//    @RequestMapping(value = "/chap2_2/hello", method = RequestMethod.GET)
    // 주소창에다가 요청 보내면 무조건 GET 요청임!!

    @GetMapping("/chap2_2/hello")  // @GetMapping은 위에 적은 @RequestMapping의 긴 코드를 짧게 작성해줌!
    @ResponseBody  // 데이터를 클라이언트에 응답
    public String hello() {
        System.out.println("GET 요청이 들어옴!!");
        return "hello spring~";
    }
    
    // JSP 응답
    @GetMapping("/chap2_2/getjsp")
//    @ResponseBody  // JSP응답이 아닌 JSON응답으로 바뀌기 때문에 @JSP 응답 시에는 @ResponseBody 쓰면 안됨!
    public String getjsp() {

        return "hello";  // JSP 파일 포워딩
    }

}
