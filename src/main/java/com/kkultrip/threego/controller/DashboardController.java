package com.kkultrip.threego.controller;

import com.kkultrip.threego.service.ask.AskService;
import com.kkultrip.threego.service.loginlog.LoginLogService;
import com.kkultrip.threego.service.place.PlaceService;
import com.kkultrip.threego.service.review.ReviewService;
import com.kkultrip.threego.service.tour.TourService;
import com.kkultrip.threego.service.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final LoginLogService loginLogService;
    private final UserService userService;
    private final PlaceService placeService;
    private final TourService tourService;
    private final ReviewService reviewService;
    private final AskService askService;

    public DashboardController(LoginLogService loginLogService, UserService userService, PlaceService placeService, TourService tourService, ReviewService reviewService, AskService askService) {
        this.loginLogService = loginLogService;
        this.userService = userService;
        this.placeService = placeService;
        this.tourService = tourService;
        this.reviewService = reviewService;
        this.askService = askService;
    }

    @GetMapping({"/", "/main"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String main(Model model){
        int visitor = loginLogService.countVisitor();
        int allUser = userService.allCount();
        int recentlyUser = userService.countRecently();
        int place = placeService.countPlace();
        int tour = tourService.countTour();
        int allReview = reviewService.countReview();
        int recentlyReview = reviewService.countRecentlyReview();
        int recentlyAsk = askService.countRecentlyAsk();
        int nullAsk = askService.countNullAsk();

        model.addAttribute("visitor", visitor);
        model.addAttribute("allUser", allUser);
        model.addAttribute("recentlyUser", recentlyUser);
        model.addAttribute("place", place);
        model.addAttribute("tour", tour);
        model.addAttribute("allReview", allReview);
        model.addAttribute("recentlyReview", recentlyReview);
        model.addAttribute("recentlyAsk", recentlyAsk);
        model.addAttribute("nullAsk", nullAsk);

        return "main";
    }

}
