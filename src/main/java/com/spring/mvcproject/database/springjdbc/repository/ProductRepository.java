package com.spring.mvcproject.database.springjdbc.repository;

import com.spring.mvcproject.database.springjdbc.entity.Product;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
//@AllArgsConstructor
@RequiredArgsConstructor  // final 필드만 골라내서 생성자 만듬
public class ProductRepository {

    // 스프링의 JDBC를 수행하는 객체
    private final JdbcTemplate jdbcTemplate; // 의존객체는 final처리

    // 생성자가 단 1개면 자동 주입처리
//    @Autowired
//    public ProductRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    // INSERT
    public void save(Product product) {
        String sql = """
                INSERT INTO products
                    (name, price, stock_quantity, description, seller, status)
                VALUES 
                    (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription(),
                product.getSeller(),
                product.getStatus()
        );

    }

    // DELETE
    public void deleteById(Long id) {
        String sql = """
                DELETE FROM products
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, id);
    }

    // UPDATE
    public void updatePrice(Long id, int newPrice) {
        String sql = """
                UPDATE products
                SET price = ?
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, newPrice, id);
    }

}
