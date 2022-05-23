package com.kkultrip.threego.repository.place;

import com.kkultrip.threego.model.Place;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class PlaceDao implements PlaceRepo{

    private final JdbcTemplate jdbcTemplate;

    public PlaceDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Place> placeRowMapper() {
        return (rs, rowNum) -> {
            Place place = new Place();
            place.setId(rs.getLong("id"));
            place.setName(rs.getString("name"));
            place.setLatitude(rs.getString("latitude"));
            place.setLongitude(rs.getString("longitude"));
            place.setAddress(rs.getString("address"));
            place.setDescription(rs.getString("description"));
            place.setGuide(rs.getString("guide"));
            place.setDate(rs.getString("date").substring(0,19));
            place.setActive(rs.getBoolean("active"));

            return place;
        };
    }

    @Override
    public Place save(Place place) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("place").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", place.getName());
        parameters.put("latitude", place.getLatitude());
        parameters.put("longitude", place.getLongitude());
        parameters.put("address", place.getAddress());
        parameters.put("description", place.getDescription());
        parameters.put("guide", place.getGuide());
        parameters.put("date", new Date());
        parameters.put("active", true);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        place.setId(key.longValue());
        return place;
    }

    @Override
    public List<Place> findAll() {
        return jdbcTemplate.query("select * from place order by id desc", placeRowMapper());
    }

    @Override
    public Optional<Place> findById(Long id) {
        List<Place> result = jdbcTemplate.query("select * from place where id = ?", placeRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Place> findByActive(boolean active) {
        return jdbcTemplate.query("select * from place where active = ?", placeRowMapper(), active);
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from place", Integer.class);
    }

    @Override
    public List<Place> findAllPage(int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from place order by id desc limit ?, ?", placeRowMapper(), startOffset, indexSize);
    }

    @Override
    public int countByPlacename(String name) {
        return jdbcTemplate.queryForObject("select count(*) from place where name like ?", Integer.class, "%" + name + "%");
    }

    @Override
    public int countByPlaceaddress(String address) {
        return jdbcTemplate.queryForObject("select count(*) from place where address like ?", Integer.class, "%" + address + "%");
    }

    @Override
    public List<Place> findByPlacenamePage(String name, int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from place where name like ? order by id desc limit ?, ?",
                placeRowMapper(), "%" + name + "%" ,startOffset, indexSize);
    }

    @Override
    public List<Place> findByPlaceaddressPage(String address, int startOffset, int indexSize) {
        return jdbcTemplate.query("select * from place where address like ? order by id desc limit ?, ?",
                placeRowMapper(), "%" + address + "%" ,startOffset, indexSize);
    }

    @Override
    public int updateActive(Long id, boolean active) {
        return jdbcTemplate.update("update place set active = ? where id = ?", active, id);
    }

    @Override
    public int updateById(Place place) {
        return jdbcTemplate.update("update place set name = ?, latitude = ?, longitude = ?, address = ?, description = ?, guide = ? where id = ?",
                place.getName(), place.getLatitude(), place.getLongitude(), place.getAddress(), place.getDescription(), place.getGuide(), place.getId());
    }
}
