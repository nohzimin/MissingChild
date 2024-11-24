package com.mycom.myapp.domain.post.controller;


import com.mycom.myapp.domain.admin.entity.Admin;
import com.mycom.myapp.domain.post.dto.SecretPostAnswerRequestDto;
import com.mycom.myapp.domain.post.dto.SecretPostCreateRequestDto;
import com.mycom.myapp.domain.post.dto.SecretPostDto;
import com.mycom.myapp.domain.post.service.SecretPostService;
import com.mycom.myapp.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secret-post")
@RequiredArgsConstructor
public class SecretPostController {

    private final SecretPostService secretPostService;

    // 사용자: 자신의 게시글 조회
    @GetMapping("/user")
    public ResponseEntity<Page<SecretPostDto>> getUserPosts(
            @AuthenticationPrincipal User user,
            Pageable pageable) {
        return ResponseEntity.ok(secretPostService.getUserPosts(user.getUserId(), pageable));
    }

    // 작성자 & 관리자: 게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<SecretPostDto> getPostDetails(
            @PathVariable Long postId,
            @AuthenticationPrincipal Object userOrAdmin) {
        boolean isAdmin = userOrAdmin instanceof Admin; // 로그인한 사용자가 관리자인지 확인
        Long userId = isAdmin ? null : ((User) userOrAdmin).getUserId();

        SecretPostDto postDto = secretPostService.getPostDetails(postId, userId, isAdmin);
        return ResponseEntity.ok(postDto);
    }


    // 관리자: 모든 게시글 조회
    @GetMapping("/admin")
    public ResponseEntity<Page<SecretPostDto>> getAllPosts(Pageable pageable) {
        return ResponseEntity.ok(secretPostService.getAllPosts(pageable));
    }

    // 관리자: 상태별 게시글 조회
    @GetMapping("/admin/status")
    public ResponseEntity<Page<SecretPostDto>> getPostsByStatus(
            @RequestParam("status") String status,
            Pageable pageable) {
        return ResponseEntity.ok(secretPostService.getPostsByStatus(status, pageable));
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<SecretPostDto> createPost(
            @AuthenticationPrincipal User user,
            @RequestBody SecretPostCreateRequestDto requestDto) {
        return ResponseEntity.ok(secretPostService.createPost(user.getUserId(), requestDto));
    }

    // 관리자: 답변 작성
    @PutMapping("/{postId}/answer")
    public ResponseEntity<SecretPostDto> addAnswer(
            @PathVariable Long postId,
            @AuthenticationPrincipal Admin admin,
            @RequestBody SecretPostAnswerRequestDto answerDto) {
        return ResponseEntity.ok(secretPostService.addAnswer(postId, admin.getAdminId(), answerDto));
    }
}
