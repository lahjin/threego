package com.kkultrip.threego.service.api;

import com.kkultrip.threego.dto.PageDto;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.model.Review;
import com.kkultrip.threego.repository.api.ReviewApiRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewApiService {

    private final ReviewApiRepo repo;

    public ReviewApiService(ReviewApiRepo repo) {
        this.repo = repo;
    }

    public Long save(Review review){
        review = repo.save(review);
        return review.getId();
    }

    public int update(Review review){
        return repo.update(review);
    }

    public int delete(Long id){
        return repo.delete(id);
    }

    public Optional<Review> findById(Long id){
        return repo.findById(id);
    }

    public int countAll(){
        return repo.countAll();
    }

    public List<Review> findAllPage(int startOffset, int indexSize){
        return repo.findAllPage(startOffset, indexSize);
    }

    public int updateGood(Long id){
        return repo.updateGood(id);
    }

    public int updateBad(Long id){
        return repo.updateBad(id);
    }

    public int countUserReview(Long user_id){
        return repo.countByUserId(user_id);
    }

    public List<Review> findByUserId(Long user_id){
        return repo.findByUserId(user_id);
    }

    public Page paging(PageDto pageDto){
        if (pageDto.getSearchCondition() == null || pageDto.getSearchKeyword() == null){
            int indexCount = countAll();
            return new Page(pageDto.getPageIndex(), 10, indexCount);
        }

        return null;
    }
}
