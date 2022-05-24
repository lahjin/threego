package com.kkultrip.threego.service.tour;

import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.model.Tour;
import com.kkultrip.threego.repository.tour.TourRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {

    private final TourRepo tourRepo;

    public TourService(TourRepo tourRepo) {
        this.tourRepo = tourRepo;
    }

    public Long save(Tour tour) {
        tourRepo.save(tour);
        return tour.getId();
    }

    public List<Tour> findAll() {
        return tourRepo.findAll();
    }

    public Optional<Tour> findId(Long id) {
        return tourRepo.findById(id);
    }

    public List<Tour> findCategoryID(Long categoryID) {
        return tourRepo.findByCategoryID(categoryID);
    }

    public List<Tour> findActive(boolean active) {
        return tourRepo.findByActive(active);
    }

    public int updateInfo(Tour tour) {
        return tourRepo.updateById(tour);
    }

    public int activeUpdate(Long id, boolean active) {
        return tourRepo.updateActive(id, active);
    }

    public int allCount() {
        return tourRepo.countAll();
    }

    public List<Tour> findPage(int startOffset, int indexSize) {
        return tourRepo.findAllPage(startOffset, indexSize);
    }

    public Page pageInfo(Integer pageIndex, Integer indexSize, String searchCondition, String searchKeyword) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        indexSize = indexSize == null ? 10 : indexSize;

        int indexCount = 0;

        if(searchCondition == null || searchKeyword == null || searchKeyword.equals("")) {
            indexCount = allCount();
            return new Page(pageIndex, indexSize, indexCount);
        } else {
            if(searchCondition.equals("name"))
                indexCount = tourRepo.countByName(searchKeyword);
            else if(searchCondition.equals("category_id"))
                indexCount = tourRepo.countByCategoryID(searchKeyword);
            return new Page(pageIndex, indexSize, indexCount, searchCondition, searchKeyword);
        }
    }

    public List<Tour> pageList(Page page) {
        int startOffset = (page.getPageIndex() - 1) * page.getIndexSize();
        if(page.getSearchCondition().equals("") || page.getSearchKeyword().equals(""))
            return findPage(startOffset, page.getIndexSize());
        else if(page.getSearchCondition().equals("name"))
            return tourRepo.findByNamePage(page.getSearchKeyword(), startOffset, page.getIndexSize());
        else if(page.getSearchCondition().equals("category_id"))
            return tourRepo.findByCategoryIDPage(page.getSearchKeyword(), startOffset, page.getIndexSize());
        return null;
    }

    public int countTour() {
        return tourRepo.countTour();
    }
}
