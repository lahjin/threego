package com.kkultrip.threego.controller.api;

import com.kkultrip.threego.model.Tour;
import com.kkultrip.threego.model.TourPlaces;
import com.kkultrip.threego.service.api.TourApiService;
import com.kkultrip.threego.service.api.TourPlacesApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TourApi {
    private final TourApiService service;
    private final TourPlacesApiService tpService;

    public TourApi(TourApiService service, TourPlacesApiService tpService) {
        this.service = service;
        this.tpService = tpService;
    }

    @GetMapping("/tour/list")
    public ResponseEntity<?> tourList(){
        List<Tour> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/tour/{id}")
    public ResponseEntity<?> tour(@PathVariable Long id){
        Optional<Tour> tour = service.findById(id);
        return ResponseEntity.ok(tour.get());
    }

    @GetMapping("/tour/category/{id}")
    public ResponseEntity<?> tourCategoryList(@PathVariable Long id){
        List<Tour> lists = service.findByCategoryId(id);
        return ResponseEntity.ok(lists);
    }

    @GetMapping("/tourPlaces/{id}")
    public ResponseEntity<?> tourPlacesList(@PathVariable Long id){
        List<TourPlaces> lists = tpService.tourPlacesList(id);
        return ResponseEntity.ok(lists);
    }
}
