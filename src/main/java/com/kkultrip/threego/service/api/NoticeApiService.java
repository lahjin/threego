package com.kkultrip.threego.service.api;

import com.kkultrip.threego.dto.PageDto;
import com.kkultrip.threego.model.Notice;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.repository.api.NoticeApiRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeApiService {

    private final NoticeApiRepo repo;

    public NoticeApiService(NoticeApiRepo repo) {
        this.repo = repo;
    }

    public Optional<Notice> findById(Long id){
        return repo.findById(id);
    }

    public int countAll(){
        return repo.countAll();
    }

    public List<Notice> findAllPage(int startOffset, int indexSize){
        return repo.findAllPage(startOffset, indexSize);
    }

    public Page paging(PageDto pageDto){
        if (pageDto.getSearchCondition() == null || pageDto.getSearchKeyword() == null){
            int indexCount = countAll();
            return new Page(pageDto.getPageIndex(), 10, indexCount);
        }

        return null;
    }
}
