package com.mycom.myapp.domain.post.service;

import com.mycom.myapp.domain.post.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostDto updatePost(Long postId, PostDto postDto);
    void deletePost(Long postId);
    List<PostDto> getAllPosts();
    List<PostDto> getAllPostsOrderByCreatedAtDesc();
    PostDto getPostById(Long postId);
    Page<PostDto> getAllPostsPaged(Pageable pageable);
    Page<PostDto> searchPosts(String searchCategory, String searchKeyword, Pageable pageable);
}
