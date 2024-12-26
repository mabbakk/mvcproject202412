package com.spring.mvcproject.database.springjdbc.repository;

import com.spring.mvcproject.database.springjdbc.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    // 다중 SELECT
    public List<Product> findAll() {
        return jdbcTemplate.query("""
                SELECT * FROM products
                """, (rs, rowNum) -> new Product(rs));
    }

    // 단일 SELECT
    public Product findById(Long id) {
        return jdbcTemplate.queryForObject("""
                SELECT * FROM products
                WHERE id = ?
                """,
                (rs, rowNum) -> new Product(rs),
                id
        );
    }

}
