package com.kkultrip.threego.service.api;

import com.kkultrip.threego.model.Category;
import com.kkultrip.threego.repository.api.CategoryApiRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryApiService {

    private final CategoryApiRepo repo;

    public CategoryApiService(CategoryApiRepo repo) {
        this.repo = repo;
    }

    public List<Category> findAll(){
        return repo.findAll();
    }
}
