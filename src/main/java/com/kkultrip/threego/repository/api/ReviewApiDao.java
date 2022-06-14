package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ReviewApiDao implements ReviewApiRepo{

    private final JdbcTemplate jdbcTemplate;

    public ReviewApiDao(DataSource dataSource) {
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
        parameters.put("user_id", review.getUser_id());
        parameters.put("tour_id", review.getTour_id());
        parameters.put("title", review.getTitle());
        parameters.put("content", review.getContent());
        parameters.put("date", new Date());
        parameters.put("count",0);
        parameters.put("good",0);
        parameters.put("bad",0);
        parameters.put("point", review.getPoint());
        parameters.put("active", true);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        review.setId(key.longValue());
        return review;
    }

    @Override
    public int update(Review review) {
        return jdbcTemplate.update("update review set title = ?, content = ? where id = ?", review.getTitle(), review.getContent(), review.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("update review set active = false where id = ?", id);
    }

    @Override
    public Optional<Review> findById(Long id) {
        List<Review> result = jdbcTemplate.query("select * from review where id = ? and active = true", reviewRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from review where active = true", Integer.class);
    }

    @Override
    public List<Review> findAllPage(int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from review where active = true order by id desc limit ?, ?", reviewRowMapper(), startOffset, indexSize);
    }

    @Override
    public int updateGood(Long id) {
        return jdbcTemplate.update("update review set good = good + 1 where id = ?", id);
    }

    @Override
    public int updateBad(Long id) {
        return jdbcTemplate.update("update review set bad = bad + 1 where id = ?", id);
    }

    @Override
    public int countByUserId(Long user_id) {
        return jdbcTemplate.queryForObject("select count(*) from review where user_id = ? and active = true", Integer.class, user_id);
    }

    @Override
    public List<Review> findByUserId(Long user_id) {
        return jdbcTemplate.query("select * from review where user_id = ? and active = true", reviewRowMapper(), user_id);
    }
}
