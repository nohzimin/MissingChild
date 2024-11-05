package com.mycom.myapp.domain.comment.service;

import com.mycom.myapp.domain.comment.dto.CommentDto;
import com.mycom.myapp.domain.comment.entity.Comment;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);
    CommentDto updateComment(Long commentId, CommentDto commentDto);
    void deleteComment(Long commentId);
    List<CommentDto> getCommentsByPostId(Long postId);
}
