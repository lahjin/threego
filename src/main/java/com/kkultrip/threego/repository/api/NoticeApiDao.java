package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Notice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class NoticeApiDao implements NoticeApiRepo {
    private final JdbcTemplate jdbcTemplate;

    public NoticeApiDao(DataSource dataSource) {
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
    public Optional<Notice> findById(Long id) {
        List<Notice> notices = jdbcTemplate.query("select * from notice where id = ? and active = true", noticeRowMapper(), id);
        return notices.stream().findAny();
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from notice where active = true", Integer.class);
    }

    @Override
    public List<Notice> findAllPage(int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from notice where active = true order by id desc limit ?, ?", noticeRowMapper(), startOffset, indexSize);
    }
}
