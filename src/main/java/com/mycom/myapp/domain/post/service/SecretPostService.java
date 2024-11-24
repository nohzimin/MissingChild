package com.mycom.myapp.domain.post.service;

import com.mycom.myapp.domain.admin.entity.Admin;
import com.mycom.myapp.domain.post.dto.SecretPostAnswerRequestDto;
import com.mycom.myapp.domain.post.dto.SecretPostCreateRequestDto;
import com.mycom.myapp.domain.post.dto.SecretPostDto;
import com.mycom.myapp.domain.post.entity.SecretPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SecretPostService {
    // 사용자가 자신의 게시글 조회
    Page<SecretPostDto> getUserPosts(Long userId, Pageable pageable);
    // 게시글 상세 조회 (작성자 또는 관리자만 접근 가능)
    SecretPostDto getPostDetails(Long postId, Long userId, boolean isAdmin);
    // 관리자가 모든 게시글 조회
    Page<SecretPostDto> getAllPosts(Pageable pageable);
    // 관리자가 상태별 게시글 조회
    Page<SecretPostDto> getPostsByStatus(String status, Pageable pageable);
    // 게시글 생성
    SecretPostDto createPost(Long userId, SecretPostCreateRequestDto requestDto);
    // 관리자 답변 추가
    SecretPostDto addAnswer(Long postId, Long adminId, SecretPostAnswerRequestDto answerDto);
}
