package com.mycom.myapp.domain.post.repository;

import com.mycom.myapp.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // PostList에서 최근순으로 정렬하기
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllOrderByCreatedAtDesc();

    // 페이지네이션
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 게시글 검색
    Page<Post> findByTitleContaining(String title, Pageable pageable);
    Page<Post> findByUserNickNameContaining(String nickname, Pageable pageable);
}
