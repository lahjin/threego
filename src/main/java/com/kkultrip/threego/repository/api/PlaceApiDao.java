package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Place;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class PlaceApiDao implements PlaceApiRepo{

    private final JdbcTemplate jdbcTemplate;

    public PlaceApiDao(DataSource dataSource) {
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
    public Optional<Place> findById(Long id) {
        List<Place> result = jdbcTemplate.query("select * from place where id = ?", placeRowMapper(), id);
        return result.stream().findAny();
    }
}
