package com.kkultrip.threego.service.category;

import com.kkultrip.threego.model.Category;
import com.kkultrip.threego.model.Notice;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.repository.category.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public Long save(Category category) {
        categoryRepo.save(category);
        return category.getId();
    }

    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    public Optional<Category> findId(Long id) {
        return categoryRepo.findById(id);
    }

    public List<Category> findNameById(Long id) {
        return categoryRepo.findByName(id);
    }

    public int updateInfo(Category category) {
        return categoryRepo.updateById(category);
    }

    public int allCount() {
        return categoryRepo.countAll();
    }

    public List<Category> findPage(int startOffset, int indexSize) {
        return categoryRepo.findAllPage(startOffset, indexSize);
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
                indexCount = categoryRepo.countByName(searchKeyword);
            return new Page(pageIndex, indexSize, indexCount, searchCondition, searchKeyword);
        }
    }

    public List<Category> pageList(Page page) {
        int startOffset = (page.getPageIndex() - 1) * page.getIndexSize();
        if(page.getSearchCondition().equals("") || page.getSearchKeyword().equals(""))
            return findPage(startOffset, page.getIndexSize());
        else if(page.getSearchCondition().equals("name"))
            return categoryRepo.findByNamePage(page.getSearchKeyword(), startOffset, page.getIndexSize());
        return null;
    }
}
