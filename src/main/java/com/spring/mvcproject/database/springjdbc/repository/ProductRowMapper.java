package com.spring.mvcproject.database.springjdbc.repository;

import com.spring.mvcproject.database.springjdbc.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// RowMapper는 데이터베이스 조회결과 테이블의 데이터를
// 자바의 객체로 자동 매핑해주는 인터페이스
public class ProductRowMapper implements RowMapper<Product> {

    // 그래서 데이터베이스의 어떤 컬럼을 자바의 어떤 필드에 넣을 건지를 정의
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product p = new Product();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getInt("price"));
        p.setDescription(rs.getString("description"));
        p.setSeller(rs.getString("seller"));
        p.setStatus(rs.getString("status"));
        p.setStockQuantity(rs.getInt("stock_quantity"));
        p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        return p;
    }
}
