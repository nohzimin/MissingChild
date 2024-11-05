package com.mycom.myapp.domain.comment.repository;


import com.mycom.myapp.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
