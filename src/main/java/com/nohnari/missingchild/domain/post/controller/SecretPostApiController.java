package com.nohnari.missingchild.domain.post.controller;


import com.nohnari.missingchild.domain.admin.entity.Admin;
import com.nohnari.missingchild.domain.admin.repository.AdminRepository;
import com.nohnari.missingchild.domain.post.dto.SecretPostAnswerRequestDto;
import com.nohnari.missingchild.domain.post.dto.SecretPostCreateRequestDto;
import com.nohnari.missingchild.domain.post.dto.SecretPostDto;
import com.nohnari.missingchild.domain.post.service.SecretPostService;
import com.nohnari.missingchild.domain.user.entity.User;
import com.nohnari.missingchild.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secret-post")
@RequiredArgsConstructor
public class SecretPostApiController {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final SecretPostService secretPostService;


    // 사용자: 자신의 게시글 조회
    @GetMapping("/user")
    public ResponseEntity<Page<SecretPostDto>> getUserPosts(
            Pageable pageable, HttpSession session) {

        // 세션에서 사용자 이메일 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);

        return ResponseEntity.ok(secretPostService.getUserPosts(user.getUserId(), pageable));
    }

    // 게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<SecretPostDto> getPostDetails(
            @PathVariable Long postId, HttpSession session) {

        // 세션에서 사용자 이메일 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);

        return ResponseEntity.ok(secretPostService.getPostDetails(postId, user.getUserId()));
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<SecretPostDto> createPost(
            @RequestBody SecretPostCreateRequestDto requestDto, HttpSession session) {

        // 세션에서 사용자 이메일 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);

        return ResponseEntity.ok(secretPostService.createPost(user.getUserId(), requestDto));
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<SecretPostDto> updatePost(
            @PathVariable Long postId,
            @RequestBody SecretPostCreateRequestDto requestDto,
            HttpSession session) {

        // 세션에서 사용자 이메일 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);

        return ResponseEntity.ok(secretPostService.updatePost(postId, user.getUserId(), requestDto));
    }

    // 사용자: 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            HttpSession session) {

        // 세션에서 사용자 이메일 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);

        secretPostService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }



    // 관리자 답변 작성
    @PutMapping("/{postId}/answer")
    public ResponseEntity<SecretPostDto> addAnswer(
            @PathVariable Long postId,
            @RequestBody SecretPostAnswerRequestDto answerDto, HttpSession session) {

        // 세션에서 관리자 이메일 가져옴
        String adminEmail = (String) session.getAttribute("adminEmail");
        Admin admin = adminRepository.findByAdminEmail(adminEmail);

        return ResponseEntity.ok(secretPostService.addAnswer(postId, answerDto.getAnswer(), admin.getAdminId()));
    }

    // 관리자: 답변 수정
    @PutMapping("/{postId}/answer/update")
    public ResponseEntity<SecretPostDto> updateAnswer(
            @PathVariable Long postId,
            @RequestBody SecretPostAnswerRequestDto answerDto,
            HttpSession session) {

        String adminEmail = (String) session.getAttribute("adminEmail");
        Admin admin = adminRepository.findByAdminEmail(adminEmail);

        return ResponseEntity.ok(secretPostService.updateAnswer(postId, answerDto.getAnswer(), admin.getAdminId()));
    }

    // 관리자: 답변 삭제
    @PutMapping("/{postId}/answer/delete")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long postId,
            HttpSession session) {

        String adminEmail = (String) session.getAttribute("adminEmail");
        Admin admin = adminRepository.findByAdminEmail(adminEmail);

        secretPostService.deleteAnswer(postId);
        return ResponseEntity.noContent().build();
    }
}
