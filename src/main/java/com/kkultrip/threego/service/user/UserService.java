package com.kkultrip.threego.service.user;

import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.model.User;
import com.kkultrip.threego.repository.user.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public List<User> findPage(int startOffset, int indexSize) {
        return userRepo.findAllPage(startOffset, indexSize);
    }
    public int allCount(){
        return userRepo.countAll();
    }

    public Page pageInfo(Integer pageIndex, Integer indexSize, String searchCondition, String searchKeyword) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        indexSize = indexSize == null ? 10 : indexSize;

        int indexCount = 0;

        if(searchCondition == null || searchKeyword == null || searchKeyword.equals("")) {
            indexCount = allCount();
            return new Page(pageIndex, indexSize, indexCount);
        } else {
            if(searchCondition.equals("username"))
                indexCount = userRepo.countByUsername(searchKeyword);
            else if(searchCondition.equals("nickname"))
                indexCount = userRepo.countByNickname(searchKeyword);
            return new Page(pageIndex, indexSize, indexCount, searchCondition, searchKeyword);
        }
    }

    public List<User> pageList(Page page) {
        int startOffset = (page.getPageIndex() - 1) * page.getIndexSize();
        if(page.getSearchCondition().equals("") || page.getSearchKeyword().equals(""))
            return findPage(startOffset, page.getIndexSize());
        else if(page.getSearchCondition().equals("username"))
            return userRepo.findByUsernamePage(page.getSearchKeyword(), startOffset, page.getIndexSize());
        else if(page.getSearchCondition().equals("nickname"))
            return userRepo.findByNicknamePage(page.getSearchKeyword(), startOffset, page.getIndexSize());
        return null;
    }

    public int countRecently() {
        return userRepo.countRecently();
    }
}
