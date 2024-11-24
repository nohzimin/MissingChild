package com.mycom.myapp.domain.post.controller;

import com.mycom.myapp.domain.comment.dto.CommentDto;
import com.mycom.myapp.domain.comment.service.CommentService;
import com.mycom.myapp.domain.post.dto.SecretPostDto;
import com.mycom.myapp.domain.post.entity.Post;
import com.mycom.myapp.domain.post.service.PostService;
import com.mycom.myapp.domain.post.dto.PostDto;
import com.mycom.myapp.domain.post.service.SecretPostService;
import com.mycom.myapp.domain.user.entity.User;
import com.mycom.myapp.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final SecretPostService secretPostService;


    // 게시글 전체 리스트 불러오기
    // 페이지 네이션
    @GetMapping("/list")
    public String PostListPage(HttpSession session, Model model,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(required = false) String searchCategory,
                               @RequestParam(required = false) String searchKeyword) {

        // 세션에서 사용자 이메일 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);


        int pageSize = 10;  // 한 페이지당 게시글 수

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<PostDto> postsPage = postService.searchPosts(searchCategory, searchKeyword, pageable);

        model.addAttribute("postsPage", postsPage);
        model.addAttribute("searchCategory", searchCategory);
        model.addAttribute("searchKeyword", searchKeyword);

        model.addAttribute("postsPage", postsPage);


        return "PostList";
    }

    @GetMapping("/secret-post/list")
    public String SecretPostListPage(
            HttpSession session,
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        // 세션에서 사용자 이메일 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);

        if (user == null) {
            // 사용자가 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        int pageSize = 10; // 한 페이지당 게시글 수
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());

        // 로그인된 사용자의 비밀 게시글 페이징 처리
        Page<SecretPostDto> postsPage = secretPostService.getUserPosts(user.getUserId(), pageable);

        // postsPage가 null인지 확인
        if (postsPage == null) {
            throw new IllegalStateException("게시글 데이터를 로드할 수 없습니다.");
        }

        model.addAttribute("postsPage", postsPage);

        return "SecretPostList";
    }


    // 게시글 등 페이지 연결
    @GetMapping("/upload")
    public String PostUploadPage(HttpSession session, Model model) {
        // 세션에서 사용자 이메일, 자녀 아이디를 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        return "PostWrite";
    }

    // 비밀 게시글 추가 연결페이지
    @GetMapping("/secret-post/upload")
    public String SecretPostUploadPage(HttpSession session, Model model) {

        // 세션에서 사용자 이메일, 자녀 아이디를 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        return "SecretPostWrite";
    }


    // 게시글 수정 페이지 연결
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

    // 비밀 게시글 수정 페이지 연결
    @GetMapping("/secret-post/update/{postId}")
    public String SecretPostEditPage(@PathVariable Long postId, HttpSession session, Model model) {
        // 세션에서 사용자 이메일, 자녀 아이디를 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        SecretPostDto post = secretPostService.getPostDetails(postId, user.getUserId());
        model.addAttribute("post", post);

        return "SecretPostEdit";
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


    // 하나의 비밀게시글 상세 조회
    @GetMapping("/secret-post/detail/{postId}")
    public String SecretPostDetailPage(@PathVariable Long postId, HttpSession session, Model model) {
        // 세션에서 사용자 이메일 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "redirect:/login"; // 로그인되지 않은 경우 리다이렉트
        }

        // 사용자 정보 모델에 추가
        model.addAttribute("user", user);

        // 게시글 상세 정보 가져오기
        SecretPostDto post = secretPostService.getPostDetails(postId, user.getUserId());
        model.addAttribute("post", post);

        return "SecretPostDetail";
    }

}
