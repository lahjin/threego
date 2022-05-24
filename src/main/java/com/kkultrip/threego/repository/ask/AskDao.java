package com.kkultrip.threego.repository.ask;

import com.kkultrip.threego.model.Ask;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class AskDao implements AskRepo{

    private final JdbcTemplate jdbcTemplate;

    public AskDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Ask> askRowMapper() {
        return (rs, rowNum) -> {
            Ask ask = new Ask();
            ask.setId(rs.getLong("id"));
            ask.setUser_id(rs.getLong("user_id"));
            ask.setTitle(rs.getString("title"));
            ask.setContent(rs.getString("content"));
            ask.setDate(rs.getString("date").substring(0, 19));
            ask.setAnswer(rs.getString("answer"));
            ask.setActive(rs.getBoolean("active"));
            return ask;
        };
    }

    @Override
    public Ask save(Ask ask) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("ask").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", ask.getTitle());
        parameters.put("content", ask.getContent());
        parameters.put("answer", ask.getAnswer());
        parameters.put("date", new Date());
        parameters.put("active", true);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        ask.setId(key.longValue());
        return ask;
    }

    @Override
    public List<Ask> findAll() {
        return jdbcTemplate.query("select * from ask order by id desc", askRowMapper());
    }

    @Override
    public Optional<Ask> findById(Long id) {
        List<Ask> result = jdbcTemplate.query("select * from ask where id = ?", askRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Ask> findByUserID(Long user_id) {
        return jdbcTemplate.query("select * from ask where user_id = ?", askRowMapper(), user_id);
    }

    @Override
    public List<Ask> findByTitle(String title) {
        return jdbcTemplate.query("select * from ask where title LIKE '%?%'", askRowMapper(), title);
    }

    @Override
    public List<Ask> findByActive(boolean active) {
        return jdbcTemplate.query("select * from ask where active = ?", askRowMapper(), active);
    }

    @Override
    public int updateById(Ask ask) {
        return jdbcTemplate.update("update ask set answer = ? where id = ?",
                ask.getAnswer(), ask.getId());
    }

    @Override
    public int updateActive(Long id, boolean active) {
        return jdbcTemplate.update("update ask set active = ? where id = ?", active, id);
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from ask", Integer.class);
    }

    @Override
    public List<Ask> findAllPage(int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from ask order by id desc limit ?, ?", askRowMapper(), startOffset, indexSize);
    }

    @Override
    public int countByTitle(String title) {
        return jdbcTemplate.queryForObject("select count(*) from ask where title like ?", Integer.class, "%" + title + "%");
    }

    @Override
    public List<Ask> findByTitlePage(String title, int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from ask where title like ? order by id desc limit ?, ?",
                askRowMapper(), "%" + title + "%" ,startOffset, indexSize);
    }

    @Override
    public int countByUserID(String user_id) {
        return jdbcTemplate.queryForObject("select count(*) from ask where user_id like ?", Integer.class, "%" + user_id + "%");
    }

    @Override
    public List<Ask> findByUserIDPage(String user_id, int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from ask where user_id like ? order by id desc limit ?, ?",
                askRowMapper(), "%" + user_id + "%" ,startOffset, indexSize);
    }

    @Override
    public int countRecentlyAsk() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM ask where DATE > TIMESTAMPADD(DAY, -7, NOW())", Integer.class);
    }

    @Override
    public int countNullAsk() {
        return jdbcTemplate.queryForObject("select count(*) from ask where answer is null", Integer.class);
    }
}
