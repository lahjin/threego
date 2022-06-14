package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CategoryApiDao implements CategoryApiRepo {

    private final JdbcTemplate jdbcTemplate;

    public CategoryApiDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Category> categoryRowMapper() {
        return (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getLong("id"));
            category.setName(rs.getString("name"));
            return category;
        };
    }
    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("select * from category", categoryRowMapper());
    }
}
