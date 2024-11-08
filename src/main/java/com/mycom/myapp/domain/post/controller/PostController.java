package com.mycom.myapp.domain.post.controller;

import com.mycom.myapp.domain.comment.dto.CommentDto;
import com.mycom.myapp.domain.comment.service.CommentService;
import com.mycom.myapp.domain.post.service.PostService;
import com.mycom.myapp.domain.post.dto.PostDto;
import com.mycom.myapp.domain.user.entity.User;
import com.mycom.myapp.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserRepository userRepository;


    @GetMapping("/list")
    public String PostListPage(HttpSession session, Model model) {
        // 세션에서 사용자 이메일, 자녀 아이디를 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        List<PostDto> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);


        return "PostList";
    }


    // 게시글 수정
    @GetMapping("/edit/{postId}")
    public String PostEditPage(@PathVariable Long postId, HttpSession session, Model model) {
        // 세션에서 사용자 이메일, 자녀 아이디를 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        PostDto post = postService.getPostById(postId);
        model.addAttribute("post", post);

        return "PostEdit";
    }

    // 하나의 게시글 상세 조회
    @GetMapping("/detail/{postId}")
    public String PostDetailPage(@PathVariable Long postId, HttpSession session, Model model) {
        // 세션에서 사용자 이메일, 자녀 아이디를 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        PostDto post = postService.getPostById(postId);
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        System.out.println(comments);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);

        model.addAttribute("userId", user != null ? user.getUserId() : null);

        return "PostDetail";
    }


    // 게시글 삭제
    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/board"; // 삭제 후 게시판 목록으로 이동
    }



}
