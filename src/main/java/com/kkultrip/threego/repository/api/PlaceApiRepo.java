package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Place;

import java.util.Optional;

public interface PlaceApiRepo {
    Optional<Place> findById(Long id);

}
