package com.kkultrip.threego.service.ask;

import com.kkultrip.threego.model.Ask;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.repository.ask.AskRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AskService {

    private final AskRepo askRepo;

    public AskService(AskRepo askRepo) {
        this.askRepo = askRepo;
    }

    public List<Ask> findAll() {
        return askRepo.findAll();
    }

    public Optional<Ask> findId(Long id) {
        return askRepo.findById(id);
    }

    public List<Ask> findUser_Id(Long user_id) {
        return askRepo.findByUserID(user_id);
    }

    public List<Ask> findTitle(String title) {
        return askRepo.findByTitle(title);
    }

    public List<Ask> findActive(boolean active) {
        return askRepo.findByActive(active);
    }

    public int updateInfo(Ask ask) {
        return askRepo.updateById(ask);
    }

    public int activeUpdate(Long id, boolean active) {
        return askRepo.updateActive(id, active);
    }

    public int allCount() {
        return askRepo.countAll();
    }

    public List<Ask> findPage(int startOffset, int indexSize) {
        return askRepo.findAllPage(startOffset, indexSize);
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
                indexCount = askRepo.countByTitle(searchKeyword);
            else if(searchCondition.equals("user_id"))
                indexCount = askRepo.countByUserID(searchKeyword);
            return new Page(pageIndex, indexSize, indexCount, searchCondition, searchKeyword);
        }
    }

    public List<Ask> pageList(Page page) {
        int startOffset = (page.getPageIndex() - 1) * page.getIndexSize();
        if(page.getSearchCondition().equals("") || page.getSearchKeyword().equals(""))
            return findPage(startOffset, page.getIndexSize());
        else if(page.getSearchCondition().equals("title"))
            return askRepo.findByTitlePage(page.getSearchKeyword(), startOffset, page.getIndexSize());
        else if(page.getSearchCondition().equals("user_id"))
            return askRepo.findByUserIDPage(page.getSearchKeyword(), startOffset, page.getIndexSize());
        return null;
    }

    public int countRecentlyAsk() {
        return askRepo.countRecentlyAsk();
    }

    public int countNullAsk() {
        return askRepo.countNullAsk();
    }
}
