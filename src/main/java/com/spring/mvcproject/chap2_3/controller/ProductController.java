package com.spring.mvcproject.chap2_3.controller;

import com.spring.mvcproject.chap2_3.entity.Product;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductController {
    
    // 가상의 메모리 상품 저장소
    private Map<Long, Product> productStore = new HashMap<>();

    // 생성자
    public ProductController() {
        productStore.put(1L, new Product(1L, "에어컨", 1000000));
        productStore.put(2L, new Product(2L, "세탁기", 1500000));
        productStore.put(3L, new Product(3L, "공기청정기", 200000));
    }


    //============================================================================================
    // 특정 상품 조회 : GET
//    @GetMapping("/products")
//    public String getProduct(HttpServletRequest req) {
//        String id = req.getParameter("id");
//        String price = req.getParameter("price");
//
//        System.out.println("/products?id=%s  : GET 요청이 들어옴!".formatted(id));
//        System.out.println("id = " + id);
//        System.out.println("price = " + price);
//        return "";
//    }


    //  /products?id=8&price=12000
//    @GetMapping("/products")
//    public String getProduct(
////        @RequestParam("id") Long id,
////        @RequestParam("price") int price
//            //를 아래와 같이 바꿔도 됨!
//        Long id,
//        int price
//    ) {
//
//        System.out.println("/products?id=%s  : GET 요청이 들어옴!".formatted(id));
//        System.out.println("id = " + id);
//        System.out.println("price = " + price);
//        return "";
//    }

    //  /products?id=8&price=12000
//    @GetMapping("/products")
//    public String getProduct(
//            Long id,
//            @RequestParam(required = false, defaultValue = "1000") int price
//    ) {
//
//        System.out.println("/products?id=%s  : GET 요청이 들어옴!".formatted(id));
//        System.out.println("id = " + id);
//        System.out.println("price = " + price);
//        return "";
//    }


//============================================================================================
    //  /products?id=8&price=12000
    @GetMapping("/products/{id}")
    @ResponseBody
    public Product getProduct( // String 타입 Product 로 변경
            @PathVariable("id") Long id  // ("id")는 생략 가능. @PathVariabled을 삭제하면 ?___로 작성해야됨.
    ) {
        System.out.println("/products/%s  : GET 요청이 들어옴!".formatted(id));
        System.out.println("id = " + id);

        Product product = productStore.get(id);
        return product;
    }

}
