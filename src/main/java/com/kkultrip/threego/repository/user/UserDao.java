package com.kkultrip.threego.repository.user;

import com.kkultrip.threego.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Repository
public class UserDao implements UserRepo{

    private final JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setNickname(rs.getString("nickname"));
            user.setRegDate(rs.getDate("reg_date"));
            user.setActive(rs.getBoolean("active"));

            return user;
        };
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from user order by id desc", userRowMapper());
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
    }

    @Override
    public List<User> findAllPage(int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from user order by id desc limit ?, ?", userRowMapper(), startOffset, indexSize);
    }

    @Override
    public int countByUsername(String username) {
        return jdbcTemplate.queryForObject("select count(*) from user where username like ?", Integer.class, "%" + username + "%");
    }

    @Override
    public int countByNickname(String nickname) {
        return jdbcTemplate.queryForObject("select count(*) from user where nickname like ?", Integer.class, "%" + nickname + "%");
    }

    @Override
    public List<User> findByUsernamePage(String username, int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from user where username like ? order by id desc limit ?, ?",
                userRowMapper(), "%" + username + "%" ,startOffset, indexSize);
    }

    @Override
    public List<User> findByNicknamePage(String nickname, int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from user where nickname like ? order by id desc limit ?, ?",
                userRowMapper(), "%" + nickname + "%" ,startOffset, indexSize);
    }

}
