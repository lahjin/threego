package com.kkultrip.threego.repository.loginlog;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class LoginLogDao implements LoginLogRepo{

    private final JdbcTemplate jdbcTemplate;

    public LoginLogDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int countVisitor() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM loginlog where DATE > TIMESTAMPADD(DAY, -7, NOW())", Integer.class);
    }
}
