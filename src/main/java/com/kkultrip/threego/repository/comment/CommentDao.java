package com.kkultrip.threego.repository.comment;

import com.kkultrip.threego.model.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class CommentDao implements CommentRepo{

    private final JdbcTemplate jdbcTemplate;

    public CommentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Comment> commentRowMapper() {
        return (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setReview_id(rs.getLong("review_id"));
            comment.setUser_id(rs.getLong("user_id"));
            comment.setContent(rs.getString("content"));
            comment.setDate(rs.getString("date").substring(0,19));
            comment.setActive(rs.getBoolean("active"));
            return comment;
        };
    }
    @Override
    public Comment save(Comment comment) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("review").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("content", comment.getContent());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        comment.setId(key.longValue());
        return comment;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        List<Comment> result = jdbcTemplate.query("select * from comment where id = ?", commentRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Comment> findByReviewId(Long review_id) {
        return jdbcTemplate.query("select * from comment where review_id = ?", commentRowMapper(), review_id);
    }


    @Override
    public List<Comment> findByUserId(Long user_id) {
        return jdbcTemplate.query("select * from comment where user_id = ?", commentRowMapper(), user_id);
    }

    @Override
    public List<Comment> findByActive(boolean active) {
        return jdbcTemplate.query("select * from comment where active = ?", commentRowMapper(), active);
    }

    @Override
    public List<Comment> findAll() {
        return jdbcTemplate.query("select * from comment order by id desc", commentRowMapper());
    }
}
