package com.nohnari.missingchild.domain.post.service;

import com.nohnari.missingchild.domain.admin.entity.Admin;
import com.nohnari.missingchild.domain.admin.repository.AdminRepository;
import com.nohnari.missingchild.domain.post.dto.SecretPostCreateRequestDto;
import com.nohnari.missingchild.domain.post.dto.SecretPostDto;
import com.nohnari.missingchild.domain.post.entity.SecretPost;
import com.nohnari.missingchild.domain.post.repository.SecretPostRepository;
import com.nohnari.missingchild.domain.user.entity.User;
import com.nohnari.missingchild.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SecretPostServiceImpl implements SecretPostService {

    private final SecretPostRepository secretPostRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    // 사용자가 자신의 게시글 조회
    @Override
    public Page<SecretPostDto> getUserPosts(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return secretPostRepository.findAllByUser(user, pageable)
                .map(post -> toDto(post));
    }

    // 게시글 상세 조회 (작성자 또는 관리자)
    @Override
    public SecretPostDto getPostDetails(Long postId, Long userId) {
        SecretPost post = secretPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 작성자가 아니고 관리자가 아니라면 접근 불가
        if (!post.getUser().getUserId().equals(userId)) {
            throw new SecurityException("해당 게시글을 조회할 권한이 없습니다.");
        }

        return toDto(post);
    }

    // 관리자가 모든 게시글 조회
    @Override
    public Page<SecretPostDto> getAllPosts(Pageable pageable) {
        return secretPostRepository.findAll(pageable)
                .map(post -> toDto(post));
    }

    // 관리자가 상태별 게시글 조회
    @Override
    public Page<SecretPostDto> getPostsByStatus(String status, Pageable pageable) {
        return secretPostRepository.findAllByStatus(status, pageable)
                .map(post -> toDto(post));
    }

    // 게시글 생성
    @Override
    @Transactional
    public SecretPostDto createPost(Long userId, SecretPostCreateRequestDto requestDto) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        SecretPost secretPost = new SecretPost();
        secretPost.setTitle(requestDto.getTitle());
        secretPost.setContent(requestDto.getContent());
        secretPost.setUser(author);
        secretPost.setCreatedAt(LocalDateTime.now());
        secretPost.setUpdatedAt(LocalDateTime.now());

        SecretPost savedPost = secretPostRepository.save(secretPost);
        return toDto(savedPost);
    }

    // 게시글 수정
    @Override
    @Transactional
    public SecretPostDto updatePost(Long postId, Long userId, SecretPostCreateRequestDto requestDto) {
        SecretPost secretPost = secretPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 작성자가 아니면 수정 불가
        if (!secretPost.getUser().getUserId().equals(userId)) {
            throw new SecurityException("게시글을 수정할 권한이 없습니다.");
        }

        secretPost.setTitle(requestDto.getTitle());
        secretPost.setContent(requestDto.getContent());
        secretPost.setUpdatedAt(LocalDateTime.now());

        SecretPost updatedPost = secretPostRepository.save(secretPost);

        return toDto(updatedPost);

    }

    // 게시글 삭제
    @Override
    @Transactional
    public void deletePost(Long postId) {
        SecretPost post = secretPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        secretPostRepository.delete(post);
    }

    // 관리자 답변 추가
    @Override
    @Transactional
    public SecretPostDto addAnswer(Long postId, String answer, Long adminId) {
        SecretPost post = secretPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Admin admin = adminRepository.findByAdminId(adminId);
        if (admin == null) {
            new IllegalArgumentException("관리자를 찾을 수 없습니다.");
        }

        post.setAnswer(answer);
        post.setAdmin(admin);
        post.setAnswerCreatedAt(LocalDateTime.now());
        post.setAnswerUpdatedAt(LocalDateTime.now());
        post.setStatus("완료");

        SecretPost updatedPost = secretPostRepository.save(post);
        return toDto(updatedPost);
    }

    // 관리자 답변 수정
    @Override
    public SecretPostDto updateAnswer(Long postId, String answer, Long adminId) {
        SecretPost secretPost = secretPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!secretPost.getAdmin().getAdminId().equals(adminId)) {
            throw new SecurityException("게시글을 수정할 권한이 없습니다.");
        }

        secretPost.setAnswer(answer);
        secretPost.setAnswerUpdatedAt(LocalDateTime.now());

        SecretPost updatedPost = secretPostRepository.save(secretPost);
        return toDto(updatedPost);
    }

    // 관리자 답변 삭제
    @Override
    @Transactional
    public SecretPostDto deleteAnswer(Long postId) {
        SecretPost post = secretPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        post.setAnswer(null);
        post.setAdmin(null);
        post.setUpdatedAt(LocalDateTime.now());
        post.setStatus("미완료");

        SecretPost deleteAnswer = secretPostRepository.save(post);
        return toDto(deleteAnswer);
    }

    // DTO 변환 유틸리티
    private SecretPostDto toDto(SecretPost post) {
        return new SecretPostDto(
                post.getSecretPostId(),
                post.getTitle(),
                post.getContent(),
                post.getAnswer() != null ? "완료" : "미완료", // 답변 여부에 따라 상태 결정
                post.getAnswer(),
                post.getCreatedAt().toString(),
                post.getUpdatedAt().toString(),
                post.getAnswerCreatedAt() != null ? post.getAnswerCreatedAt().toString() : null,
                post.getAnswerUpdatedAt() != null ? post.getAnswerUpdatedAt().toString() : null,
                post.getUser().getName()
        );
    }
}
