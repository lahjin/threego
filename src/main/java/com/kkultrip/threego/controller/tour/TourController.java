package com.kkultrip.threego.controller.tour;

import com.kkultrip.threego.config.mvc.NavList;
import com.kkultrip.threego.model.Category;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.model.Tour;
import com.kkultrip.threego.service.category.CategoryService;
import com.kkultrip.threego.service.tour.TourService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
public class TourController {

    private final TourService tourService;

    private final CategoryService categoryService;

    public TourController(TourService tourService, CategoryService categoryService) {
        this.tourService = tourService;
        this.categoryService = categoryService;
    }

    @GetMapping("/tour")
    public String tour(
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "indexSize", required = false) Integer indexSize,
            @RequestParam(value = "searchCondition", required = false) String searchCondition,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            Model model
    ) {
        Page page = tourService.pageInfo(pageIndex, indexSize, searchCondition, searchKeyword);

        List<Tour> tour = tourService.pageList(page);

        model.addAttribute("tour", tour);
        model.addAttribute("page", page);

        NavList.navTour(model);
        return "tour/tour";
    }

    @GetMapping("/tour/info/{id}")
    public String tourInfoId(@PathVariable Long id, Model model) {
        Optional<Tour> tour = tourService.findId(id);

        model.addAttribute("tour", tour.get());


        NavList.navTour(model);
        return "tour/info";
    }

    @GetMapping("/tour/edit/{id}")
    public String tourEditId(@PathVariable Long id, Model model) {
        Optional<Tour> tour = tourService.findId(id);
        List<Category> category = categoryService.findAll();

        tour.get().setDescription(tour.get().getDescription().replace("<br/>", "\r\n"));
        model.addAttribute("tour", tour.get());
        model.addAttribute("category", category);

        NavList.navTour(model);
        return "tour/edit";
    }

    @PostMapping("/tour/edit")
    public String tourEdit(Tour _tour, Model model) {
        _tour.setDescription(_tour.getDescription().replace("\r\n", "<br/>"));

        int rs = tourService.updateInfo(_tour);

        Optional<Tour> tour = tourService.findId(_tour.getId());
        model.addAttribute("tour", tour.get());

        NavList.navTour(model);
        return "tour/info";
    }

    @GetMapping("/tour/active/{id}")
    public String tourActiveId(@PathVariable Long id, Model model) {
        Optional<Tour> tour = tourService.findId(id);

        int rs = tourService.activeUpdate(id, !tour.get().isActive());
        if(rs == 1) {
            tour.get().setActive(!tour.get().isActive());
            model.addAttribute("tour", tour.get());
            NavList.navTour(model);
            return "tour/info";
        }
        else
            return "tour/error";
    }

    @GetMapping("/tour/write")
    public String tourWrite(Model model) {
        List<Category> category = categoryService.findAll();

        model.addAttribute("tour", new Tour());
        model.addAttribute("mode", "write");
        model.addAttribute("category", category);

        NavList.navTour(model);
        return "tour/edit";
    }

    @PostMapping("/tour/add")
    public String tourAdd(@Valid Tour _tour, Model model) {
        _tour.setDescription(_tour.getDescription().replace("\r\n", "<br/>"));

        Long rs = tourService.save(_tour);

        Optional<Tour> tour = tourService.findId(_tour.getId());
        model.addAttribute("tour", tour.get());

        NavList.navTour(model);
        return "tour/info";
    }
}
