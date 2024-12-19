package com.spring.mvcproject.score.routes;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
// 성적 관리에 필요한 JSP 파일들을 포워딩 하는 컨트롤러
public class ScorePageController {

//    @GetMapping("/score/page")
//    public String scorePage(Model model) {
//
//        // 해당 JSP 파일의 경로를 찍음
//        model.addAttribute("title", "성적 관리");
//        model.addAttribute("foods", List.of("짜장", "떡볶이", "오렌지"));
//
//        return "score/score-page";   // application.properties에 접두사 접미사 설정해놨기 때문에 앞뒤 코드가 생략될 수 있었던 거임!
//    }

    @GetMapping("/score/page")
    public ModelAndView scorePage() {

        ModelAndView mv = new ModelAndView();
        // 해당 JSP파일의 경로를 적음
        mv.addObject("title", "성적 관리");
        mv.addObject("foods", List.of("짜장", "떡볶이", "오렌지"));

        mv.setViewName("score/score-page");
        return mv;

    }

}
