package com.kkultrip.threego.service.api;

import com.kkultrip.threego.model.Place;
import com.kkultrip.threego.repository.api.PlaceApiRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceApiService {

    private final PlaceApiRepo repo;

    public PlaceApiService(PlaceApiRepo repo) {
        this.repo = repo;
    }

    public Optional<Place> findById(Long id){
        return repo.findById(id);
    }
}
