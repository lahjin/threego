package com.kkultrip.threego.repository.tour;

import com.kkultrip.threego.model.Tour;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class TourDao implements TourRepo {

    private final JdbcTemplate jdbcTemplate;

    public TourDao(DataSource dataSource) {
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
    public Tour save(Tour tour) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("tour").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", tour.getName());
        parameters.put("description", tour.getDescription());
        parameters.put("date", new Date());
        parameters.put("active", true);
        parameters.put("category_id", tour.getCategory_id());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        tour.setId(key.longValue());
        return tour;
    }

    @Override
    public List<Tour> findAll() {
        return jdbcTemplate.query("select * from tour order by id desc", tourRowMapper());
    }

    @Override
    public Optional<Tour> findById(Long id) {
        List<Tour> result = jdbcTemplate.query("select * from tour where id = ?", tourRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Tour> findByCategoryID(Long categoryID) {
        return jdbcTemplate.query("select * from tour where category_id = ?", tourRowMapper(), categoryID);
    }

    @Override
    public List<Tour> findByActive(boolean active) {
        return jdbcTemplate.query("select * from tour where active = ?", tourRowMapper(), active);
    }

    @Override
    public int updateById(Tour tour) {
        return jdbcTemplate.update("update tour set name = ?, description = ?, category_id = ?, active = ? where id = ?",
                tour.getName(), tour.getDescription(), tour.getCategory_id(), tour.isActive(), tour.getId());
    }

    @Override
    public int updateActive(Long id, boolean active) {
        return jdbcTemplate.update("update tour set active = ? where id = ?", active, id);
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from tour", Integer.class);
    }

    @Override
    public List<Tour> findAllPage(int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from tour order by id desc limit ?, ?", tourRowMapper(), startOffset, indexSize);
    }

    @Override
    public int countByName(String name) {
        return jdbcTemplate.queryForObject("select count(*) from tour where name like ?", Integer.class, "%" + name + "%");
    }

    @Override
    public List<Tour> findByNamePage(String name, int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from tour where name like ? order by id desc limit ?, ?",
                tourRowMapper(), "%" + name + "%" ,startOffset, indexSize);
    }

    @Override
    public int countByCategoryID(String categoryID) {
        return jdbcTemplate.queryForObject("select count(*) from tour where category_id like ?", Integer.class, "%" + categoryID + "%");
    }

    @Override
    public List<Tour> findByCategoryIDPage(String categoryID, int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from tour where category_id like ? order by id desc limit ?, ?",
                tourRowMapper(), "%" + categoryID + "%" ,startOffset, indexSize);
    }
}
