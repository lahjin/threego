package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Tour;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class TourApiDao implements TourApiRepo{

    private final JdbcTemplate jdbcTemplate;

    public TourApiDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Tour> tourRowMapper() {
        return (rs, rowNum) -> {
            Tour tour = new Tour();
            tour.setId(rs.getLong("id"));
            tour.setName(rs.getString("name"));
            tour.setDescription(rs.getString("description"));
            tour.setDate(rs.getString("date").substring(0, 19));
            tour.setActive(rs.getBoolean("active"));
            tour.setCategory_id(rs.getLong("category_id"));
            return tour;
        };
    }

    @Override
    public Optional<Tour> findById(Long id) {
        List<Tour> result = jdbcTemplate.query("select * from tour where id = ?", tourRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from tour where active = true", Integer.class);
    }

    @Override
    public List<Tour> findAllPage(int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from tour where active = true order by id desc limit ?, ?", tourRowMapper(), startOffset, indexSize);
    }

    @Override
    public List<Tour> findAll() {
        return jdbcTemplate.query("select * from tour where active = true", tourRowMapper());
    }

    @Override
    public List<Tour> findByCategoryId(Long id) {
        return jdbcTemplate.query("select * from tour where active = true and category_id = ?", tourRowMapper(), id);
    }

}
