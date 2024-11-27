package com.nohnari.missingchild.domain.post.controller;

import com.nohnari.missingchild.domain.admin.entity.Admin;
import com.nohnari.missingchild.domain.admin.repository.AdminRepository;
import com.nohnari.missingchild.domain.post.dto.SecretPostDto;
import com.nohnari.missingchild.domain.post.entity.SecretPost;
import com.nohnari.missingchild.domain.post.repository.SecretPostRepository;
import com.nohnari.missingchild.domain.post.service.SecretPostService;
import com.nohnari.missingchild.domain.user.entity.User;
import com.nohnari.missingchild.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/secret-post")
@RequiredArgsConstructor
public class AdminSecretPostPageController {

    private final SecretPostRepository secretPostRepository;
    private final SecretPostService secretPostService;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;


    @GetMapping
    public String getSecretPostList(
            @RequestParam(value = "status", required = false) String status,
            Pageable pageable,
            Model model, HttpSession session) {

        Page<SecretPostDto> posts = (status != null)
                ? secretPostService.getPostsByStatus(status, pageable)
                : secretPostService.getAllPosts(pageable);

        model.addAttribute("posts", posts);

        // 세션에서 관리자 이메일 가져옴
        String adminEmail = (String) session.getAttribute("adminEmail");
        Admin admin = adminRepository.findByAdminEmail(adminEmail);
        model.addAttribute("admin", admin);
        return "adminSecretPost";
    }

    @GetMapping("/detail/{postId}")
    public String getSecretPostDetail(@PathVariable Long postId, Model model, HttpSession session) {
        // 세션에서 사용자 이메일 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        // 세션에서 관리자 이메일 가져옴
        String adminEmail = (String) session.getAttribute("adminEmail");
        Admin admin = adminRepository.findByAdminEmail(adminEmail);
        model.addAttribute("admin", admin);

        SecretPost post = secretPostRepository.findBySecretPostId(postId);

//        SecretPostDto post = secretPostService.getPostDetails(postId, user.getUserId());
        model.addAttribute("post", post);
        return "adminSecretPostDetail";
    }
}
