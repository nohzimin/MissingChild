package com.nohnari.missingchild.domain.comment.repository;


import com.nohnari.missingchild.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_PostId(Long postId);

    @Query("select count(c) from Comment c where c.post.postId = :postId")
    Long countByPostId(Long postId);
}
