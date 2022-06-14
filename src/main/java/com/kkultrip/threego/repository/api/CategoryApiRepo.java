package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Category;

import java.util.List;

public interface CategoryApiRepo {
    List<Category> findAll();
}
