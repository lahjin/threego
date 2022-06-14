package com.kkultrip.threego.service.api;

import com.kkultrip.threego.model.TourPlaces;
import com.kkultrip.threego.repository.api.TourPlaceApiRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourPlacesApiService {

    private final TourPlaceApiRepo repo;

    public TourPlacesApiService(TourPlaceApiRepo repo) {
        this.repo = repo;
    }

    public List<TourPlaces> tourPlacesList(Long id){
        return repo.findByTourId(id);
    }
}
