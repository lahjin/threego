package com.kkultrip.threego.service.api;

import com.kkultrip.threego.model.Tour;
import com.kkultrip.threego.repository.api.TourApiRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourApiService {

    private final TourApiRepo repo;

    public TourApiService(TourApiRepo repo) {
        this.repo = repo;
    }

    public Optional<Tour> findById(Long id){
        return repo.findById(id);
    }

    public List<Tour> findPageAll(int startOffset, int endPage){
        return repo.findAllPage(startOffset, endPage);
    }

    public List<Tour> findAll(){
        return repo.findAll();
    }

    public List<Tour> findByCategoryId(Long id){
        return repo.findByCategoryId(id);
    }
}
