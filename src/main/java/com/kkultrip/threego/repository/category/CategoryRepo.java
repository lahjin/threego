package com.kkultrip.threego.repository.category;

import com.kkultrip.threego.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo {
    Category save(Category category);
    List<Category> findAll();
    Optional<Category> findById(Long id);
    List<Category> findByName(Long id);
    int updateById(Category category);
    int countAll();
    List<Category> findAllPage(int startOffset, int indexSize);
    int countByName(String name);
    List<Category> findByNamePage(String name, int startOffset, int indexSize);
}
