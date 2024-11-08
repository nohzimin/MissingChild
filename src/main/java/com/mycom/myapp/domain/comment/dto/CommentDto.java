package com.mycom.myapp.domain.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long commentId;
    private String commentContent;
    private Long userId; // User ID만 포함
    private Long postId; // Post ID만 포함
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String authorNickname; // 댓글 작성자 닉네임 추가
}
