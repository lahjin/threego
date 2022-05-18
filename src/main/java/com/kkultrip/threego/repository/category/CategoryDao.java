package com.kkultrip.threego.repository.category;

import com.kkultrip.threego.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class CategoryDao implements CategoryRepo{

    private final JdbcTemplate jdbcTemplate;

    public CategoryDao(DataSource dataSource) {
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
    public Category save(Category category) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("category").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", category.getName());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        category.setId(key.longValue());
        return category;
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("select * from category order by id desc", categoryRowMapper());
    }

    @Override
    public Optional<Category> findById(Long id) {
        List<Category> result = jdbcTemplate.query("select * from category where id = ?", categoryRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Category> findByName(Long id) {
        return jdbcTemplate.query("select * from category where id = ?", categoryRowMapper(), id);
    }

    @Override
    public int updateById(Category category) {
        return jdbcTemplate.update("update * set name = ? where id =?",
                category.getName(), category.getId());
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from category", Integer.class);
    }

    @Override
    public List<Category> findAllPage(int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from category order by id desc limit ?, ?", categoryRowMapper(), startOffset, indexSize);
    }

    @Override
    public int countByName(String name) {
        return jdbcTemplate.queryForObject("select count(*) from category where name like ?", Integer.class, "%" + name + "%");
    }

    @Override
    public List<Category> findByNamePage(String name, int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from category where name like ? order by id desc limit ?, ?",
                categoryRowMapper(), "%" + name + "%" ,startOffset, indexSize);
    }
}
