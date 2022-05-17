package com.kkultrip.threego.repository.review;

import com.kkultrip.threego.model.Comment;
import com.kkultrip.threego.model.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ReviewDao implements ReviewRepo{

    private final JdbcTemplate jdbcTemplate;

    public ReviewDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Review> reviewRowMapper() {
        return (rs, rowNum) -> {
            Review review = new Review();
            review.setId(rs.getLong("id"));
            review.setUser_id(rs.getLong("user_id"));
            review.setTour_id(rs.getLong("tour_id"));
            review.setTitle(rs.getString("title"));
            review.setContent(rs.getString("content"));
            review.setDate(rs.getString("date").substring(0,19));
            review.setCount(rs.getInt("count"));
            review.setGood(rs.getInt("good"));
            review.setBad(rs.getInt("bad"));
            review.setPoint(rs.getString("point"));
            review.setActive(rs.getBoolean("active"));

            return review;
        };
    }

    @Override
    public Review save(Review review) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("review").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", review.getTitle());
        parameters.put("content", review.getContent());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        review.setId(key.longValue());
        return review;
    }

    @Override
    public Optional<Review> findById(Long id) {
        List<Review> result = jdbcTemplate.query("select * from review where id = ?", reviewRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Review> findByUserId(Long user_id) {
        return jdbcTemplate.query("select * from review where user_id = ?", reviewRowMapper(), user_id);
    }

    @Override
    public List<Review> findByTourId(Long tour_id) {
        return jdbcTemplate.query("select * from review where tour_id = ?", reviewRowMapper(), tour_id);
    }

    @Override
    public List<Review> findByTitle(String title) {
        return jdbcTemplate.query("select * from review where title LIKE '%?%'", reviewRowMapper(), title);
    }


    @Override
    public List<Review> findByActive(boolean active) {
        return jdbcTemplate.query("select * from review where active = ?", reviewRowMapper(), active);
    }


    @Override
    public List<Review> findAll() {
        return jdbcTemplate.query("select * from review order by id desc", reviewRowMapper());
    }

    @Override
    public int updateById(Review review) {
        return jdbcTemplate.update("update review set active = ? where id = ?", review.isActive(), review.getId());
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from review", Integer.class);
    }

    @Override
    public List<Review> findAllPage(int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from review order by id desc limit ?, ?", reviewRowMapper(), startOffset, indexSize);
    }

    @Override
    public int updateActive(Long id, boolean active) {
        return jdbcTemplate.update("update review set active = ? where id = ?", active, id);
    }

    @Override
    public int countByTitle(String title) {
        return jdbcTemplate.queryForObject("select count(*) from review where title like ?", Integer.class, "%" + title + "%");
    }

    @Override
    public List<Review> findByTitlePage(String title, int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from review where title like ? order by id desc limit ?, ?",
                reviewRowMapper(), "%" + title + "%" ,startOffset, indexSize);
    }
}
