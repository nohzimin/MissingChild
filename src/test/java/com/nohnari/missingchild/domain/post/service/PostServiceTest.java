package com.nohnari.missingchild.domain.post.service;

import com.nohnari.missingchild.domain.comment.repository.CommentRepository;
import com.nohnari.missingchild.domain.post.dto.PostDto;
import com.nohnari.missingchild.domain.post.entity.Post;
import com.nohnari.missingchild.domain.post.repository.PostRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class PostServiceTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class CreatePost {

        @Test
        void 게시글_생성_성공() {
            // given
            User user = new User();
            user.setUserId(1L);
            user.setNickName("작성자");

            Post post = new Post();
            post.setPostId(1L);
            post.setTitle("제목");
            post.setContent("내용");
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            post.setUser(user);

            PostDto postDto = new PostDto();
            postDto.setTitle("제목");
            postDto.setContent("내용");
            postDto.setUserId(1L);

            given(userRepository.findById(1L)).willReturn(Optional.of(user));
            given(postRepository.save(org.mockito.ArgumentMatchers.any(Post.class))).willReturn(post);

            // when
            PostDto createdPost = postService.createPost(postDto);

            // then
            assertThat(createdPost.getTitle()).isEqualTo("제목");
            assertThat(createdPost.getContent()).isEqualTo("내용");
            assertThat(createdPost.getUserId()).isEqualTo(1L);
            assertThat(createdPost.getAuthorNickname()).isEqualTo("작성자");
        }

        @Test
        void 존재하지_않는_사용자로_게시글_생성_실패() {
            // given
            PostDto postDto = new PostDto();
            postDto.setTitle("제목");
            postDto.setContent("내용");
            postDto.setUserId(99L);

            given(userRepository.findById(99L)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> postService.createPost(postDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("존재하지 않는 사용자입니다.");
        }
    }

    @Nested
    class GetPostById {

        @Test
        void 게시글_조회_성공() {
            // given
            User user = new User();
            user.setUserId(1L);
            user.setNickName("작성자");

            Post post = new Post();
            post.setPostId(1L);
            post.setTitle("제목");
            post.setContent("내용");
            post.setUser(user);
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());

            given(postRepository.findById(1L)).willReturn(Optional.of(post));

            // when
            PostDto postDto = postService.getPostById(1L);

            // then
            assertThat(postDto.getPostId()).isEqualTo(1L);
            assertThat(postDto.getTitle()).isEqualTo("제목");
            assertThat(postDto.getAuthorNickname()).isEqualTo("작성자");
        }

        @Test
        void 존재하지_않는_게시글_조회_실패() {
            // given
            given(postRepository.findById(99L)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> postService.getPostById(99L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("존재하지 않는 게시글입니다.");
        }
    }

    @Nested
    class GetAllPosts {

        @Test
        void 전체_게시글_조회_성공() {
            // given
            User user = new User();
            user.setUserId(1L);
            user.setNickName("작성자");

            Post post1 = new Post();
            post1.setPostId(1L);
            post1.setTitle("제목1");
            post1.setUser(user);

            Post post2 = new Post();
            post2.setPostId(2L);
            post2.setTitle("제목2");
            post2.setUser(user);

            given(postRepository.findAll()).willReturn(Arrays.asList(post1, post2));

            // when
            List<PostDto> posts = postService.getAllPosts();

            // then
            assertThat(posts).hasSize(2);
            assertThat(posts.get(0).getTitle()).isEqualTo("제목1");
            assertThat(posts.get(1).getTitle()).isEqualTo("제목2");
        }
    }

    @Nested
    class SearchPosts {

        @Test
        void 제목으로_검색_성공() {
            // given
            Pageable pageable = Pageable.unpaged();
            User user = new User();
            user.setUserId(1L);
            user.setNickName("작성자");

            Post post = new Post();
            post.setPostId(1L);
            post.setTitle("제목");
            post.setUser(user);

            Page<Post> postPage = new PageImpl<>(List.of(post));
            given(postRepository.findByTitleContaining("제목", pageable)).willReturn(postPage);
            given(commentRepository.countByPostId(1L)).willReturn(5L);

            // when
            Page<PostDto> result = postService.searchPosts("title", "제목", pageable);

            // then
            assertThat(result.getContent()).hasSize(1);
            assertThat(result.getContent().get(0).getTitle()).isEqualTo("제목");
            assertThat(result.getContent().get(0).getCommentCount()).isEqualTo(5L);
        }

        @Test
        void 검색어_없음_전체_조회() {
            // given
            Pageable pageable = Pageable.unpaged();
            User user = new User();
            user.setUserId(1L);
            user.setNickName("작성자");

            Post post = new Post();
            post.setPostId(1L);
            post.setTitle("제목");
            post.setUser(user);

            Page<Post> postPage = new PageImpl<>(List.of(post));
            given(postRepository.findAll(pageable)).willReturn(postPage);

            // when
            Page<PostDto> result = postService.searchPosts(null, "", pageable);

            // then
            assertThat(result.getContent()).hasSize(1);
            assertThat(result.getContent().get(0).getTitle()).isEqualTo("제목");
        }
    }
}