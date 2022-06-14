package com.kkultrip.threego.controller.api;

import com.kkultrip.threego.dto.PlaceIndexDto;
import com.kkultrip.threego.model.Place;
import com.kkultrip.threego.service.api.PlaceApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PlaceApi {

    private final PlaceApiService service;

    public PlaceApi(PlaceApiService service) {
        this.service = service;
    }

    @GetMapping("/place/{id}")
    public ResponseEntity<?> place(@PathVariable Long id){
        Optional<Place> place = service.findById(id);
        return ResponseEntity.ok(place.get());
    }

    @PostMapping("/place")
    public ResponseEntity<?> place(@RequestBody PlaceIndexDto dto){
        int[] index = dto.getIndex();
        List<Place> list = new ArrayList<>();
        for(int i=0; i<index.length; i++){
            Optional<Place> place = service.findById((long) index[i]);
            list.add(place.get());
        }
        return ResponseEntity.ok(list);
    }
}
