package com.spring.mvcproject.chap2_5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class ResponseController {

    // 페이지 라우팅 - 특정 뷰(JSP, Thymeleaf)를 포워딩(열어주는것)
    @GetMapping("/pet")
    public String pet() {
        return "pet";
    }

    // html응답
    @GetMapping("/show/html")
    @ResponseBody  // 페이지 라우팅을 하지 않고, 순수 리턴데이터를 클라이언트에 전송
    public String html() {
        return """
                <html>
                <body>
                    <h1>메롱메롱 HTML</h1>
                </body>
                </html>
                """;
    }

    // 텍스트 응답
    @GetMapping(value = "/show/text", produces = "text/plain")
    @ResponseBody
    public String text() {
        return "하이 나는 문자야~";
    }

    // JSON 배열 응답 - 자바의 배열이나 리스트를 리턴하면
    @GetMapping("/json/hobbies")
    @ResponseBody
    public List<String> hobbies() {
        return List.of("수영", "농구", "낮잠", "탁구");
    }
    // JSON 객체 응답 - 자바의 Map이나 클래스의 객체를 리턴
    @GetMapping("/json/my-cat")
    @ResponseBody
    public Map<String, Object> myCat() {
        return Map.of(
                "name", "나옹이",
                "age", 3,
                "injection", false
        );
    }

    @GetMapping("/json/my-cat2")
    @ResponseBody
    public Cat myCat2() {
        return new Cat("야옹양옹", 5, true);
    }

    // JSON 객체 배열
    @GetMapping("/json/my-cats")
    @ResponseBody
    public List<Cat> Cats() {   // 자주 사용할 때 사용!
        return List.of(
                new Cat("옹냥이", 3, false),
                new Cat("냥냥이", 2, true),
                new Cat("뇽냥이", 7, true),
                new Cat("뿅냥이", 5, false)
        );
    }

    // html를 요청할 때 - JSP !
    // 데이터를 요청할 때 - 라우팅!
}
