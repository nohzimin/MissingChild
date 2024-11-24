package com.mycom.myapp.domain.post.repository;

import com.mycom.myapp.domain.post.entity.Post;
import com.mycom.myapp.domain.post.entity.SecretPost;
import com.mycom.myapp.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecretPostRepository extends JpaRepository<SecretPost, Long> {
    // 사용자가 작성한 게시글 조회
    Page<SecretPost> findAllByAuthor(User author, Pageable pageable);

    // 관리자가 조회하는 모든 게시글 (필터링 없음)
    Page<SecretPost> findAll(Pageable pageable);

    // 관리자가 상태별로 게시글 조회
    Page<SecretPost> findAllByStatus(String status, Pageable pageable);

}
