package com.kkultrip.threego.repository.comment;

import com.kkultrip.threego.model.Comment;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface CommentRepo {

    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
    List<Comment> findByReviewId(Long review_id);
    List<Comment> findByUserId(Long user_id);
    List<Comment> findByActive(boolean active);
    List<Comment> findAll();
}
