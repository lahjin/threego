package com.kkultrip.threego.service.notice;

import com.kkultrip.threego.model.Notice;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.repository.notice.NoticeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    private final NoticeRepo noticeRepo;

    public NoticeService(NoticeRepo noticeRepo) {
        this.noticeRepo = noticeRepo;
    }

    public List<Notice> findAll() {
        return noticeRepo.findAll();
    }

    public Optional<Notice> findId(Long id) {
        return noticeRepo.findById(id);
    }

    public List<Notice> findTitle(String title) {
        return noticeRepo.findByTitle(title);
    }

    public List<Notice> findActive(boolean active) {
        return noticeRepo.findByActive(active);
    }

    public int updateInfo(Notice notice) {
        return noticeRepo.updateById(notice);
    }
    public int activeUpdate(Long id, boolean active) {
        return noticeRepo.updateActive(id, active);
    }

    public int allCount(){
        return noticeRepo.countAll();
    }

    public List<Notice> findPage(int startOffset, int indexSize) {
        return noticeRepo.findAllPage(startOffset, indexSize);
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
                indexCount = noticeRepo.countByTitle(searchKeyword);
            return new Page(pageIndex, indexSize, indexCount, searchCondition, searchKeyword);
        }
    }

    public List<Notice> pageList(Page page) {
        int startOffset = (page.getPageIndex() - 1) * page.getIndexSize();
        if(page.getSearchCondition().equals("") || page.getSearchKeyword().equals(""))
            return findPage(startOffset, page.getIndexSize());
        else if(page.getSearchCondition().equals("title"))
            return noticeRepo.findByTitlePage(page.getSearchKeyword(), startOffset, page.getIndexSize());
        return null;
    }
}
