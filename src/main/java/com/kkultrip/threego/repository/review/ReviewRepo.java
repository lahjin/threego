package com.kkultrip.threego.repository.review;

import com.kkultrip.threego.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo {
    Review save(Review review);
    Optional<Review> findById(Long id);
    List<Review> findByUserId(Long user_id);
    List<Review> findByTourId(Long tour_id);
    List<Review> findByTitle(String title);

    List<Review> findByActive(boolean active);

    List<Review> findAll();

    int updateById(Review review);

    int countAll();
    List<Review> findAllPage(int startOffset, int indexSize);

    int countByTitle(String title);
    List<Review> findByTitlePage(String title, int startOffset, int indexSize);

    int updateActive(Long id, boolean active);
}
