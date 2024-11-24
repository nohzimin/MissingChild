package com.mycom.myapp.domain.post.service;

import com.mycom.myapp.domain.admin.entity.Admin;
import com.mycom.myapp.domain.admin.repository.AdminRepository;
import com.mycom.myapp.domain.post.dto.SecretPostAnswerRequestDto;
import com.mycom.myapp.domain.post.dto.SecretPostCreateRequestDto;
import com.mycom.myapp.domain.post.dto.SecretPostDto;
import com.mycom.myapp.domain.post.entity.SecretPost;
import com.mycom.myapp.domain.post.repository.SecretPostRepository;
import com.mycom.myapp.domain.user.entity.User;
import com.mycom.myapp.domain.user.repository.UserRepository;
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
        return secretPostRepository.findAllByAuthor(user, pageable)
                .map(post -> toDto(post, false));
    }

    // 게시글 상세 조회 (작성자 또는 관리자 만 접근 가능)
    @Override
    public SecretPostDto getPostDetails(Long postId, Long userId, boolean isAdmin) {
        SecretPost post = secretPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 관리자이거나 작성자가 아니면 접근 권한 없음
        if (!isAdmin && !post.getAuthor().getUserId().equals(userId)) {
            throw new SecurityException("해당 게시글을 조회할 권한이 없습니다.");
        }

        return toDto(post, isAdmin);
    }

    // 관리자가 모든 게시글 조회
    @Override
    public Page<SecretPostDto> getAllPosts(Pageable pageable) {
        return secretPostRepository.findAll(pageable)
                .map(post -> toDto(post, true));
    }

    // 관리자가 상태별 게시글 조회
    @Override
    public Page<SecretPostDto> getPostsByStatus(String status, Pageable pageable) {
        return secretPostRepository.findAllByStatus(status, pageable)
                .map(post -> toDto(post, true));
    }

    // 게시글 생성
    @Override
    @Transactional
    public SecretPostDto createPost(Long userId, SecretPostCreateRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        SecretPost secretPost = new SecretPost();
        secretPost.setTitle(requestDto.getTitle());
        secretPost.setContent(requestDto.getContent());
        secretPost.setAuthor(user);

        SecretPost savedPost = secretPostRepository.save(secretPost);
        return toDto(savedPost, false);
    }

    // 관리자 답변 추가
    @Override
    @Transactional
    public SecretPostDto addAnswer(Long postId, Long adminId, SecretPostAnswerRequestDto answerDto) {
        SecretPost post = secretPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        Admin admin = adminRepository.findByAdminId(adminId); // Admin 타입 직접 반환
        if (admin == null) {
            throw new IllegalArgumentException("관리자를 찾을 수 없습니다.");
        }

        post.setAnswer(answerDto.getAnswer());
        post.setAdmin(admin);
        post.setStatus("완료");
        post.setUpdatedAt(LocalDateTime.now());

        SecretPost updatedPost = secretPostRepository.save(post);
        return toDto(updatedPost, true);
    }

    // DTO 변환 유틸리티
    private SecretPostDto toDto(SecretPost post, boolean isAdmin) {
        return new SecretPostDto(
                post.getSecretPostId(),
                post.getTitle(),
                post.getContent(),
                isAdmin ? post.getStatus() : null,
                isAdmin ? post.getAnswer() : null,
                post.getCreatedAt().toString(),
                post.getUpdatedAt().toString(),
                post.getAuthor().getName()
        );
    }
}
