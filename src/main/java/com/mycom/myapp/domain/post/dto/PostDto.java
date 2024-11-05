package com.mycom.myapp.domain.post.dto;

import com.mycom.myapp.domain.comment.dto.CommentDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {
    private Long postId;
    private String title;
    private String content;
    private Long userId; // User ID만 포함하여 참조 방지
    private List<CommentDto> comments; // 필요 시 댓글 목록 포함
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
