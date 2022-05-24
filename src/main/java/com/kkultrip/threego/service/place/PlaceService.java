package com.kkultrip.threego.service.place;

import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.model.Place;
import com.kkultrip.threego.repository.place.PlaceRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {
    private final PlaceRepo placeRepo;

    public PlaceService(PlaceRepo placeRepo) {
        this.placeRepo = placeRepo;
    }

    public Long save(Place place) {
        placeRepo.save(place);
        return place.getId();
    }

    public List<Place> findAll(){
        return placeRepo.findAll();
    }

    public List<Place> findPage(int startOffset, int indexSize) {
        return placeRepo.findAllPage(startOffset, indexSize);
    }
    public int allCount(){
        return placeRepo.countAll();
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
                indexCount = placeRepo.countByPlacename(searchKeyword);
            else if(searchCondition.equals("address"))
                indexCount = placeRepo.countByPlaceaddress(searchKeyword);
            return new Page(pageIndex, indexSize, indexCount, searchCondition, searchKeyword);
        }
    }

    public List<Place> pageList(Page page) {
        int startOffset = (page.getPageIndex() - 1) * page.getIndexSize();
        if(page.getSearchCondition().equals("") || page.getSearchKeyword().equals(""))
            return findPage(startOffset, page.getIndexSize());
        else if(page.getSearchCondition().equals("name"))
            return placeRepo.findByPlacenamePage(page.getSearchKeyword(), startOffset, page.getIndexSize());
        else if(page.getSearchCondition().equals("address"))
            return placeRepo.findByPlaceaddressPage(page.getSearchKeyword(), startOffset, page.getIndexSize());
        return null;
    }

    public Optional<Place> findId(Long id) {
        return placeRepo.findById(id);
    }

    public int activeUpdate(Long id, boolean active) {
        return placeRepo.updateActive(id, active);
    }

    public int updateInfo(Place place) {
        return placeRepo.updateById(place);
    }

    public int countPlace() {
        return placeRepo.countPlace();
    }
}
