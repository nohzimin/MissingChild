package com.mycom.myapp.domain.post.entity;

import com.mycom.myapp.domain.admin.entity.Admin;
import com.mycom.myapp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "secret_post")
@Data
public class SecretPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long secretPostId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column(nullable = false)
    private String status = "미완료"; // 기본값: 미완료

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // user_id가 null일 수 없음
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private LocalDateTime answerCreatedAt;

    @Column(nullable = true)
    private LocalDateTime answerUpdatedAt;

}