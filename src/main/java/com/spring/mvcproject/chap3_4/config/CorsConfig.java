package com.spring.mvcproject.chap3_4.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // 요청을 허용할 서버 URL
                .addMapping("/api/v1/*")
                
                // 요청을 허용할 클라이언트 URL
                .allowedOrigins("http://127.0.0.1:5500")
                
                // 요청을 허용할 요청 방식
                .allowedMethods("GET", "POST")
                .allowedMethods("*")
                ;
    }
}

// 프론트엔드 서버 따로 만들고 서버로부터 데이터를 받기 위해 fetch 하려면 이 설정을 해야한다!!
