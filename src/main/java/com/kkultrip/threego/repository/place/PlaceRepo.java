package com.kkultrip.threego.repository.place;

import com.kkultrip.threego.model.Place;

import java.util.List;
import java.util.Optional;

public interface PlaceRepo {
    Place save(Place place);

    List<Place> findAll();

    Optional<Place> findById(Long id);

    List<Place> findByActive(boolean active);

    int countAll();

    List<Place> findAllPage(int startOffset, int indexSize);

    int countByPlacename(String name);

    int countByPlaceaddress(String address);

    List<Place> findByPlacenamePage(String name, int startOffset, int indexSize);

    List<Place> findByPlaceaddressPage(String address, int startOffset, int indexSize);

    int updateActive(Long id, boolean active);

    int updateById(Place place);
}
