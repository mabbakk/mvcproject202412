package com.spring.mvcproject.chap4_2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    // 뷰(JSP, Thymeleaf) 포워딩
    @GetMapping("/chap4-2/pet")
    public String pet(Model model) {
        model.addAttribute("username", "꼬끼오");
        return "pet";
    }
}
