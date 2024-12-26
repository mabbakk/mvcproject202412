package com.spring.mvcproject.database.springjdbc.repository;

import com.spring.mvcproject.database.springjdbc.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void saveTest() {

        Product p = Product.builder()
                .name("청소기")
                .stockQuantity(3)
                .price(30000)
                .description("진공 청소기 우왕~~")
                .seller("청소왕")
                .status("ACTIVE")
                .build();

//        Product p = new Product(null, "에어컨", 1500000, 0, "휘센 시원~~", "윌리스 캐리어", "SOLD_OUT", null);
//        p.setName("세탁기");
//        p.setPrice(2000000);
//        p.setSeller("김또치");
//        p.setDescription("세상에서 제일 잘 세탁하는 기계입니다.");
//        p.setStockQuantity(13);
//        p.setStatus("ACTIVE");

        productRepository.save(p);
    }

    @Test
    void deleteTest() {
        Long id = 3L;

        productRepository.deleteById(id);
    }

    @Test
    void updatePriceTest() {
        productRepository.updatePrice(2L, 9999);
    }

}