package com.kkultrip.threego.service.review;

import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.model.Review;
import com.kkultrip.threego.repository.review.ReviewRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepo reviewRepo;

    public ReviewService(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    public List<Review> findAll(){
        return reviewRepo.findAll();
    }

    public Optional<Review> findId(Long id){
        return reviewRepo.findById(id);
    }

    public List<Review> findUserId(Long user_id){
        return reviewRepo.findByUserId(user_id);
    }

    public List<Review> findTourId(Long tour_id){
        return reviewRepo.findByTourId(tour_id);
    }

    public List<Review> findTitle(String title){
        return reviewRepo.findByTitle(title);
    }

    public List<Review> findActive(boolean active){
        return reviewRepo.findByActive(active);
    }

    public int updateInfo(Review review){
        return reviewRepo.updateById(review);
    }

    public int allCount(){
        return reviewRepo.countAll();
    }

    public List<Review> findPage(int startOffset, int indexSize) {
        return reviewRepo.findAllPage(startOffset, indexSize);
    }
    public Page pageInfo(Integer pageIndex, Integer indexSize, String searchCondition, String searchKeyword) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        indexSize = indexSize == null ? 10 : indexSize;

        int indexCount = 0;

        if(searchCondition == null || searchKeyword == null || searchKeyword.equals("")) {
            indexCount = allCount();
            return new Page(pageIndex, indexSize, indexCount);
        } else {
            if(searchCondition.equals("title"))
                indexCount = reviewRepo.countByTitle(searchKeyword);
            return new Page(pageIndex, indexSize, indexCount, searchCondition, searchKeyword);
        }
    }

    public int activeUpdate(Long id, boolean active) {
        return reviewRepo.updateActive(id, active);
    }

    public List<Review> pageList(Page page) {
        int startOffset = (page.getPageIndex() - 1) * page.getIndexSize();
        if(page.getSearchCondition().equals("") || page.getSearchKeyword().equals(""))
            return findPage(startOffset, page.getIndexSize());
        else if(page.getSearchCondition().equals("title"))
            return reviewRepo.findByTitlePage(page.getSearchKeyword(), startOffset, page.getIndexSize());
        return null;
    }
}
