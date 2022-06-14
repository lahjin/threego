package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.TourPlaces;

import java.util.List;

public interface TourPlaceApiRepo {
    List<TourPlaces> findByTourId(Long id);
}
