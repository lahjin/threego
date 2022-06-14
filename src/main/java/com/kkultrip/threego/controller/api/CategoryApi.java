package com.kkultrip.threego.controller.api;

import com.kkultrip.threego.dto.MessageDto;
import com.kkultrip.threego.model.Category;
import com.kkultrip.threego.service.api.CategoryApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryApi {

    private final CategoryApiService service;

    public CategoryApi(CategoryApiService service) {
        this.service = service;
    }

    @GetMapping("/category")
    public ResponseEntity<?> category(){
        List<Category> lists = service.findAll();
        if(lists.isEmpty())
            return ResponseEntity.internalServerError().body(new MessageDto("인터넷 에러"));
        return ResponseEntity.ok(lists);
    }
}
