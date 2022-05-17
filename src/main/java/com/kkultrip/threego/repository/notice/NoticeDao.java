package com.kkultrip.threego.repository.notice;

import com.kkultrip.threego.model.Notice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
public class NoticeDao implements NoticeRepo{

    private final JdbcTemplate jdbcTemplate;

    public NoticeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Notice> noticeRowMapper() {
        return (rs, rowNum) -> {
            Notice notice = new Notice();
            notice.setId(rs.getLong("id"));
            notice.setTitle(rs.getString("title"));
            notice.setContent(rs.getString("content"));
            notice.setDate(rs.getString("date").substring(0, 19));
            notice.setActive(rs.getBoolean("active"));
            return notice;
        };
    }

    @Override
    public Notice save(Notice notice) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("notice").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", notice.getTitle());
        parameters.put("content", notice.getContent());
        parameters.put("date", new Date());
        parameters.put("active", true);
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        notice.setId(key.longValue());

        // PreparedStatementCreator 방식으로 기본키 찾기
        /*
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement("insert into notice(title, content) values (?, ?)", new String[]{"ID"});
            pstmt.setString(1, notice.getTitle());
            pstmt.setString(2, notice.getContent());
            return pstmt;
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        notice.setId(keyValue.longValue());
        */

        return notice;
    }

    @Override
    public List<Notice> findAll() {
        return jdbcTemplate.query("select * from notice order by id desc", noticeRowMapper());
    }

    @Override
    public Optional<Notice> findById(Long id) {
        List<Notice> result = jdbcTemplate.query("select * from notice where id = ?", noticeRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Notice> findByTitle(String title) {
        return jdbcTemplate.query("select * from notice where title LIKE '%?%'", noticeRowMapper(), title);
    }

    @Override
    public List<Notice> findByActive(boolean active) {
        return jdbcTemplate.query("select * from notice where active = ?", noticeRowMapper(), active);
    }

    @Override
    public int updateById(Notice notice) {
        return jdbcTemplate.update("update notice set title = ?, content = ?, active = ? where id = ?",
                notice.getTitle(), notice.getContent(), notice.isActive(), notice.getId());
    }

    @Override
    public int updateActive(Long id, boolean active) {
        return jdbcTemplate.update("update notice set active = ? where id = ?", active, id);
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from notice", Integer.class);
    }

    @Override
    public List<Notice> findAllPage(int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from notice order by id desc limit ?, ?", noticeRowMapper(), startOffset, indexSize);
    }

    @Override
    public int countByTitle(String title) {
        return jdbcTemplate.queryForObject("select count(*) from notice where title like ?", Integer.class, "%" + title + "%");
    }

    @Override
    public List<Notice> findByTitlePage(String title, int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from notice where title like ? order by id desc limit ?, ?",
                noticeRowMapper(), "%" + title + "%" ,startOffset, indexSize);
    }
}
