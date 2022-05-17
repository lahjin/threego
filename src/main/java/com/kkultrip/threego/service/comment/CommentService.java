package com.kkultrip.threego.service.comment;

import com.kkultrip.threego.model.Comment;
import com.kkultrip.threego.repository.comment.CommentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommentService {
    private final CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public List<Comment> findAll(){
        return commentRepo.findAll();
    }

    public Optional<Comment> findId(Long id){
        return commentRepo.findById(id);
    }

    public List<Comment> findReviewId(Long review_id){
        return commentRepo.findByReviewId(review_id);
    }

    public List<Comment> findUserId(Long user_id){
        return commentRepo.findByUserId(user_id);
    }

    public List<Comment> findActive(boolean active){
        return commentRepo.findByActive(active);
    }

}
