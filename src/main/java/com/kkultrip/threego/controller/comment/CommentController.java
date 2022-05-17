package com.kkultrip.threego.controller.comment;

import com.kkultrip.threego.config.mvc.NavList;
import com.kkultrip.threego.model.Comment;
import com.kkultrip.threego.model.Review;
import com.kkultrip.threego.service.comment.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comment")
    public String comment(Model model){
        List<Comment> list = commentService.findAll();

        model.addAttribute("comment", list);
        NavList.navReview(model);
        return "comment/comment";
    }

    @GetMapping("/comment/info/{id}")
    public String commentPage(@PathVariable Long id, Model model){

        Optional<Comment> comment = commentService.findId(id);

        model.addAttribute("comment",comment.get());
        NavList.navReview(model);
        return "comment/info";
    }
}
