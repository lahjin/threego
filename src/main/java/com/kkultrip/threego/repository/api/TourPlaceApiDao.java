package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.TourPlaces;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TourPlaceApiDao implements TourPlaceApiRepo{


    private final JdbcTemplate jdbcTemplate;

    public TourPlaceApiDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<TourPlaces> tourPlacesRowMapper() {
        return (rs, rowNum) -> {
            TourPlaces tourPlaces = new TourPlaces();
            tourPlaces.setTour_id(rs.getLong("tour_id"));
            tourPlaces.setPlace_id(rs.getLong("place_id"));
            tourPlaces.setOrder(rs.getInt("order"));
            return tourPlaces;
        };
    }

    @Override
    public List<TourPlaces> findByTourId(Long id) {
        return jdbcTemplate.query("select * from `tour-places` where tour_id = ?", tourPlacesRowMapper(), id);
    }
}
