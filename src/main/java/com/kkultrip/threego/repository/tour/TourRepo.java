package com.kkultrip.threego.repository.tour;


import com.kkultrip.threego.model.Tour;

import java.util.List;
import java.util.Optional;

public interface TourRepo {
    Tour save(Tour tour);

    List<Tour> findAll();

    Optional<Tour> findById(Long id);

    List<Tour> findByCategoryID(Long categoryID);

    List<Tour> findByActive(boolean active);

    int updateById(Tour tour);

    int updateActive(Long id, boolean active);

    int countAll();

    List<Tour> findAllPage(int startOffset, int indexSize);

    int countByName(String name);

    List<Tour> findByNamePage(String name, int startOffset, int indexSize);

    int countByCategoryID(String categoryID);

    List<Tour> findByCategoryIDPage(String categoryID, int startOffset, int indexSize);
    int countTour();
}
