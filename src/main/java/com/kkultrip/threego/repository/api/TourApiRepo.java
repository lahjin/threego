package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Tour;

import java.util.List;
import java.util.Optional;

public interface TourApiRepo {

    Optional<Tour> findById(Long id);
    int countAll();
    List<Tour> findAllPage(int startOffset, int indexSize);
    List<Tour> findAll();
    List<Tour> findByCategoryId(Long id);
}
