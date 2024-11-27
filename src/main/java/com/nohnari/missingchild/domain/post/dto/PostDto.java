package com.nohnari.missingchild.domain.post.dto;

import com.nohnari.missingchild.domain.comment.dto.CommentDto;
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
    private String authorNickname; // 작성자 닉네임 추가
    private Long commentCount; // 댓글 수를 저장할 필드 추가
}
