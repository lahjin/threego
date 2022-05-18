package com.kkultrip.threego.controller.category;

import com.kkultrip.threego.config.mvc.NavList;
import com.kkultrip.threego.model.Category;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.service.category.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String category(
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "indexSize", required = false) Integer indexSize,
            @RequestParam(value = "searchCondition", required = false) String searchCondition,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            Model model
    ) {
        Page page = categoryService.pageInfo(pageIndex, indexSize, searchCondition, searchKeyword);

        List<Category> category = categoryService.pageList(page);

        model.addAttribute("category", category);
        model.addAttribute("page", page);

        NavList.navTour(model);
        return "category/category";
    }

    @GetMapping("/category/edit/{id}")
    public String categoryEditId(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.findId(id);

        model.addAttribute("category", category.get());

        NavList.navTour(model);
        return "category/edit";
    }

    @PostMapping("/category/edit")
    public String categoryEdit(
            Category _category,
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "indexSize", required = false) Integer indexSize,
            @RequestParam(value = "searchCondition", required = false) String searchCondition,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            Model model) {
        int rs = categoryService.updateInfo(_category);

        Page page = categoryService.pageInfo(pageIndex, indexSize, searchCondition, searchKeyword);

        List<Category> category = categoryService.pageList(page);

        model.addAttribute("category", category);
        model.addAttribute("page", page);

        NavList.navTour(model);
        return "category/category";
    }

    @GetMapping("/category/write")
    public String categoryWrite(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("mode", "write");

        NavList.navTour(model);
        return "category/edit";
    }

    @PostMapping("/category/add")
    public String categoryAdd(
            Category _category,
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "indexSize", required = false) Integer indexSize,
            @RequestParam(value = "searchCondition", required = false) String searchCondition,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            Model model) {
        Long rs = categoryService.save(_category);

        Page page = categoryService.pageInfo(pageIndex, indexSize, searchCondition, searchKeyword);

        List<Category> category = categoryService.pageList(page);

        model.addAttribute("category", category);
        model.addAttribute("page", page);

        NavList.navTour(model);
        return "category/category";
    }
}
