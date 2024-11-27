package com.nohnari.missingchild.domain.post.service;

import com.nohnari.missingchild.domain.admin.entity.Admin;
import com.nohnari.missingchild.domain.admin.repository.AdminRepository;
import com.nohnari.missingchild.domain.post.dto.SecretPostCreateRequestDto;
import com.nohnari.missingchild.domain.post.dto.SecretPostDto;
import com.nohnari.missingchild.domain.post.entity.SecretPost;
import com.nohnari.missingchild.domain.post.repository.SecretPostRepository;
import com.nohnari.missingchild.domain.user.entity.User;
import com.nohnari.missingchild.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SecretPostServiceTest {

    @InjectMocks
    private SecretPostServiceImpl secretPostService;

    @Mock
    private SecretPostRepository secretPostRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminRepository adminRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class CreatePost {

        @Test
        void 게시글_생성_성공() {
            // given
            Long userId = 1L;
            User user = new User();
            user.setUserId(userId);
            user.setName("작성자");

            SecretPostCreateRequestDto requestDto = new SecretPostCreateRequestDto();
            requestDto.setTitle("제목");
            requestDto.setContent("내용");

            SecretPost secretPost = new SecretPost();
            secretPost.setSecretPostId(1L);
            secretPost.setTitle("제목");
            secretPost.setContent("내용");
            secretPost.setUser(user);
            secretPost.setCreatedAt(LocalDateTime.now());
            secretPost.setUpdatedAt(LocalDateTime.now());

            given(userRepository.findById(userId)).willReturn(Optional.of(user));
            given(secretPostRepository.save(any(SecretPost.class))).willReturn(secretPost);

            // when
            SecretPostDto result = secretPostService.createPost(userId, requestDto);

            // then
            assertThat(result.getTitle()).isEqualTo("제목");
            assertThat(result.getContent()).isEqualTo("내용");
            assertThat(result.getAuthorName()).isEqualTo("작성자");
        }

        @Test
        void 존재하지_않는_사용자_예외() {
            // given
            Long userId = 99L;
            SecretPostCreateRequestDto requestDto = new SecretPostCreateRequestDto();
            requestDto.setTitle("제목");
            requestDto.setContent("내용");

            given(userRepository.findById(userId)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> secretPostService.createPost(userId, requestDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("사용자를 찾을 수 없습니다.");
        }
    }

    @Nested
    class AddAnswer {


        @Test
        void 존재하지_않는_게시글_예외() {
            // given
            Long postId = 99L;
            Long adminId = 1L;
            String answer = "답변 내용";

            given(secretPostRepository.findById(postId)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> secretPostService.addAnswer(postId, answer, adminId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("게시글을 찾을 수 없습니다.");
        }
    }

    @Nested
    class GetUserPosts {

        @Test
        void 사용자_게시글_조회_성공() {
            // given
            Long userId = 1L;

            User user = new User();
            user.setUserId(userId);
            user.setName("작성자");

            SecretPost secretPost = new SecretPost();
            secretPost.setSecretPostId(1L);
            secretPost.setTitle("제목");
            secretPost.setContent("내용");
            secretPost.setUser(user);
            secretPost.setCreatedAt(LocalDateTime.now());
            secretPost.setUpdatedAt(LocalDateTime.now());

            Page<SecretPost> posts = new PageImpl<>(List.of(secretPost));
            given(userRepository.findById(userId)).willReturn(Optional.of(user));
            given(secretPostRepository.findAllByUser(user, Pageable.unpaged())).willReturn(posts);

            // when
            Page<SecretPostDto> result = secretPostService.getUserPosts(userId, Pageable.unpaged());

            // then
            assertThat(result.getContent()).hasSize(1);
            assertThat(result.getContent().get(0).getSecretPostId()).isEqualTo(1L);
            assertThat(result.getContent().get(0).getAuthorName()).isEqualTo("작성자");
        }
    }
}