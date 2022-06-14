package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewApiRepo {
    Review save(Review review);
    int update(Review review);
    int delete(Long id);

    Optional<Review> findById(Long id);
    int countAll();
    List<Review> findAllPage(int startOffset, int indexSize);

    int updateGood(Long id);
    int updateBad(Long id);

    int countByUserId(Long user_id);
    List<Review> findByUserId(Long user_id);
}
