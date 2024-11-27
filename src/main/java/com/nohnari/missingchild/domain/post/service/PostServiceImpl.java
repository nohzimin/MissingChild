package com.nohnari.missingchild.domain.post.service;

import com.nohnari.missingchild.domain.comment.repository.CommentRepository;
import com.nohnari.missingchild.domain.post.dto.PostDto;
import com.nohnari.missingchild.domain.post.entity.Post;
import com.nohnari.missingchild.domain.post.repository.PostRepository;
import com.nohnari.missingchild.domain.user.entity.User;
import com.nohnari.missingchild.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return convertToPostDto(savedPost);
    }

    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        existingPost.setTitle(postDto.getTitle());
        existingPost.setContent(postDto.getContent());
        existingPost.setUpdatedAt(LocalDateTime.now());

        Post updatedPost = postRepository.save(existingPost);
        return convertToPostDto(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPostsOrderByCreatedAtDesc() {
        List<Post> posts = postRepository.findAllOrderByCreatedAtDesc();
        return posts.stream()
                .map(this::convertToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        PostDto postDto = new PostDto();
        postDto.setPostId(post.getPostId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUserId(post.getUser().getUserId());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());
        postDto.setAuthorNickname(post.getUser().getNickName()); // 작성자 닉네임 설정

        return postDto;
    }

    @Override
    public Page<PostDto> getAllPostsPaged(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::convertToPostDto); // Post 엔티티를 PostDto로 변환하여 반환
    }



    // 게시글 검색
    @Override
    public Page<PostDto> searchPosts(String searchCategory, String searchKeyword, Pageable pageable) {
        Page<Post> postPage;

        if (searchKeyword == null || searchKeyword.isEmpty()) {
            postPage = postRepository.findAll(pageable);
        } else {
            switch (searchCategory) {
                case "title":
                    postPage = postRepository.findByTitleContaining(searchKeyword, pageable);
                    break;
                case "author":
                    postPage = postRepository.findByUserNickNameContaining(searchKeyword, pageable);
                    break;
                default:
                    postPage = postRepository.findAll(pageable);
            }
        }

        return postPage.map(post -> {
            PostDto dto = convertToPostDto(post);
            // 각 게시글의 댓글 수를 조회하여 설정
            Long commentCount = commentRepository.countByPostId(post.getPostId());
            dto.setCommentCount(commentCount);
            return dto;
        });
    }




    private PostDto convertToPostDto(Post post) {
        PostDto dto = new PostDto();
        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setUserId(post.getUser().getUserId());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setAuthorNickname(post.getUser().getNickName());
        return dto;
    }
}
