package com.nohnari.missingchild.domain.comment.service;

import com.nohnari.missingchild.domain.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);
    CommentDto updateComment(Long commentId, CommentDto commentDto);
    void deleteComment(Long commentId);
    List<CommentDto> getCommentsByPostId(Long postId);
}
