package com.mycom.myapp.domain.comment.service;

import com.mycom.myapp.domain.comment.dto.CommentDto;
import com.mycom.myapp.domain.comment.entity.Comment;
import com.mycom.myapp.domain.comment.repository.CommentRepository;
import com.mycom.myapp.domain.post.entity.Post;
import com.mycom.myapp.domain.post.repository.PostRepository;
import com.mycom.myapp.domain.user.entity.User;
import com.mycom.myapp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Comment comment = new Comment();
        comment.setCommentContent(commentDto.getCommentContent());
        comment.setUser(user);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return convertToCommentDto(savedComment);
    }

    @Override
    public CommentDto updateComment(Long commentId, CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        existingComment.setCommentContent(commentDto.getCommentContent());
        existingComment.setUpdatedAt(LocalDateTime.now());

        Comment updatedComment = commentRepository.save(existingComment);
        return convertToCommentDto(updatedComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPost_PostId(postId);
        return comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            commentDto.setCommentId(comment.getCommentId());
            commentDto.setCommentContent(comment.getCommentContent());
            commentDto.setUserId(comment.getUser().getUserId());
            commentDto.setPostId(comment.getPost().getPostId());
            commentDto.setCreatedAt(comment.getCreatedAt());
            commentDto.setUpdatedAt(comment.getUpdatedAt());
            commentDto.setAuthorNickname(comment.getUser().getNickName()); // 댓글 작성자 닉네임 설정
            return commentDto;
        }).collect(Collectors.toList());
    }

    private CommentDto convertToCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setCommentId(comment.getCommentId());
        dto.setCommentContent(comment.getCommentContent());
        dto.setUserId(comment.getUser().getUserId());
        dto.setPostId(comment.getPost().getPostId());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setAuthorNickname(comment.getUser().getNickName());
        return dto;
    }
}
