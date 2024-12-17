package com.spring.mvcproject.score.routes;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// 성적 관리에 필요한 JSP 파일들을 포워딩 하는 컨트롤러
public class ScorePageController {

    @GetMapping("/score/page")
    public String scorePage() {
        
        // 해당 JSP 파일의 경로를 찍음
        return "score/score-page";   // application.properties에 접두사 접미사 설정해놨기 때문에 앞뒤 코드가 생략될 수 있었던 거임!
    }
}
