package com.kkultrip.threego.controller.review;

import com.kkultrip.threego.config.mvc.NavList;
import com.kkultrip.threego.model.Comment;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.model.Review;
import com.kkultrip.threego.service.comment.CommentService;
import com.kkultrip.threego.service.review.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final CommentService commentService;

    public ReviewController(ReviewService reviewService, CommentService commentService) {
        this.reviewService = reviewService;
        this.commentService = commentService;
    }


    @GetMapping("/review")
    public String Review(
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "indexSize", required = false) Integer indexSize,
            @RequestParam(value = "searchCondition", required = false) String searchCondition,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            Model model
    ){


        Page page = reviewService.pageInfo(pageIndex, indexSize, searchCondition, searchKeyword);

        List<Review> review = reviewService.pageList(page);

        model.addAttribute("review",review);
        model.addAttribute("page", page);
        NavList.navReview(model);
        return "review/review";
    }

    @GetMapping("/review/info/{id}")
    public String reviewPage(@PathVariable Long id, Model model){

        Optional<Review> review = reviewService.findId(id);
        List<Comment> comment = commentService.findReviewId(id);

        model.addAttribute("review",review.get());
        model.addAttribute("comment",comment);

        NavList.navReview(model);
        return "review/info";
    }

    @GetMapping("/review/active/{id}")
    public String reviewActive(@PathVariable Long id, Model model) {
        NavList.navReview(model);
        Optional<Review> review = reviewService.findId(id);
        List<Comment> comment = commentService.findReviewId(id);

        int rs = reviewService.activeUpdate(id, !review.get().isActive());
        if(rs == 1){
            review.get().setActive(!review.get().isActive());
            model.addAttribute("review", review.get());
            model.addAttribute("comment",comment);
            return "review/info";
        }
        else
            return "notice/error";
    }

    @GetMapping("/review/info")
    public String reviewInfo(Review reviews, Model model){
        int rs = reviewService.updateInfo(reviews);

        List<Review> review = reviewService.findAll();
        model.addAttribute("review",review);
        NavList.navReview(model);
        return  "review/review";
    }
}
