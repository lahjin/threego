package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Ask;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;


@Repository
public class AskApiDao implements AskApiRepo {

    private final JdbcTemplate jdbcTemplate;

    public AskApiDao(DataSource dataSource) {
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
        parameters.put("user_id", ask.getUser_id());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        ask.setId(key.longValue());
        return ask;
    }

    @Override
    public int update(Ask ask) {
        return jdbcTemplate.update("update ask set title = ?, content = ? where id = ?", ask.getTitle(), ask.getContent(), ask.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("update ask set active = false where id = ?", id);

    }

    @Override
    public int countByUserId(Long user_id) {
        return jdbcTemplate.queryForObject("select count(*) from ask where user_id = ? and active = true", Integer.class, user_id);
    }

    @Override
    public Optional<Ask> findById(Long id) {
        List<Ask> result = jdbcTemplate.query("select * from ask where id = ? and active = true", askRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Ask> findByUserId(Long user_id) {
        return jdbcTemplate.query("select * from ask where user_id = ? and active = true", askRowMapper(), user_id);
    }


}
