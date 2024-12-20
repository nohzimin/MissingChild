package com.nohnari.missingchild.global.controller;

import com.nohnari.missingchild.domain.admin.entity.Admin;
import com.nohnari.missingchild.domain.admin.repository.AdminRepository;
import com.nohnari.missingchild.domain.child.dto.MissingChildDto;
import com.nohnari.missingchild.domain.child.dto.MissingChildResultDto;
import com.nohnari.missingchild.domain.child.service.MissingChildService;
import com.nohnari.missingchild.domain.comment.service.CommentService;
import com.nohnari.missingchild.domain.post.service.PostService;
import com.nohnari.missingchild.domain.user.entity.User;
import com.nohnari.missingchild.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final MissingChildService missingChildService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {

        return "signup";
    }


    @GetMapping("/admin/login")
    public String adminLoginPage() {

        return "adminLogin";
    }

    @GetMapping("/admin/signup")
    public String adminSignupPage() {

        return "adminSignup";
    }

    @GetMapping("/admin")
    public String adminPage(HttpSession session, Model model) {
        // 세션에서 관리자 이메일 가져옴
        String adminEmail = (String) session.getAttribute("adminEmail");

        Admin admin = adminRepository.findByAdminEmail(adminEmail);
        MissingChildResultDto missingChildResultDto = missingChildService.getAllMissingChild();
        List<MissingChildDto> missingChildDtoList = missingChildResultDto.getMissingChildDtoList();

        model.addAttribute("admin", admin);
        model.addAttribute("missingChildren", missingChildDtoList);

        return "admin";
    }

    @GetMapping("/")
    public String mainPage(HttpSession session, Model model) {
        // 세션에서 사용자 이메일을 가져옴
        String email = (String) session.getAttribute("userEmail");

        if (email != null) {  // 세션에 저장된 이메일이 있을 경우
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }

        return "index";
    }


    // 게시글 작성
    @GetMapping("/board/write")
    public String PostWritePage(HttpSession session, Model model) {
        // 세션에서 사용자 이메일을 가져옴
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        return "PostWrite";
    }


    @GetMapping("/missing-child/list")
    public String AllMissingChildListPage(HttpSession session, Model model) {
        // 세션에서 사용자 이메일, 를 가져옴
        String email = (String) session.getAttribute("userEmail");

        if (email != null) {  // 세션에 저장된 이메일이 있을 경우
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }

        return "list";
    }


     @GetMapping("/missing-child/search")
    public String MissingChildSearchPage(HttpSession session, Model model) {
        // 세션에서 사용자 이메일을 가져옴
        String email = (String) session.getAttribute("userEmail");

        if (email != null) {  // 세션에 저장된 이메일이 있을 경우
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }

        return "search";
    }


    @GetMapping("/missing-child/register")
    public String MissingChildInsertPage(HttpSession session, Model model) {

        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        return "childInsertPage";
    }



}
