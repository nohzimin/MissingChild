package com.mycom.myapp.global.controller;

import com.mycom.myapp.domain.admin.entity.Admin;
import com.mycom.myapp.domain.admin.repository.AdminRepository;
import com.mycom.myapp.domain.user.entity.User;
import com.mycom.myapp.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

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
        System.out.println("adminEmail---------------------------");
        System.out.println(adminEmail);
        System.out.println("adminEmail---------------------------");

        Admin admin = adminRepository.findByAdminEmail(adminEmail);
        model.addAttribute("admin", admin);

        return "admin";
    }

    @GetMapping("/")
    public String mainPage(HttpSession session, Model model) {
        // 세션에서 사용자 이메일, 자녀 아이디를 가져옴
        String email = (String) session.getAttribute("userEmail");

        System.out.println("---------------------------");
        System.out.println(email);
        System.out.println("---------------------------");

        if (email != null) {  // 세션에 저장된 이메일이 있을 경우
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }

        return "index";
    }

    @GetMapping("/missing-child/list")
    public String AllMissingChildListPage(HttpSession session, Model model) {
        // 세션에서 사용자 이메일, 자녀 아이디를 가져옴
        String email = (String) session.getAttribute("userEmail");

        System.out.println("---------------------------");
        System.out.println(email);
        System.out.println("---------------------------");

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
        // 세션에서 사용자 이메일, 자녀 아이디를 가져옴
        String email = (String) session.getAttribute("userEmail");

        System.out.println("---------------------------");
        System.out.println(email);
        System.out.println("---------------------------");

        if (email != null) {  // 세션에 저장된 이메일이 있을 경우
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }

        return "search";
    }



}
