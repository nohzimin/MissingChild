package com.nohnari.missingchild.domain.comment.service;

import com.nohnari.missingchild.domain.comment.dto.CommentDto;
import com.nohnari.missingchild.domain.comment.entity.Comment;
import com.nohnari.missingchild.domain.comment.repository.CommentRepository;
import com.nohnari.missingchild.domain.comment.service.CommentServiceImpl;
import com.nohnari.missingchild.domain.post.entity.Post;
import com.nohnari.missingchild.domain.post.repository.PostRepository;
import com.nohnari.missingchild.domain.user.entity.User;
import com.nohnari.missingchild.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Nested
    class CreateComment {

        @Test
        void 댓글_생성_성공() {
            // given
            CommentDto commentDto = new CommentDto();
            commentDto.setUserId(1L);
            commentDto.setPostId(2L);
            commentDto.setCommentContent("Test comment");

            User user = new User();
            user.setUserId(1L);
            user.setNickName("Author");

            Post post = new Post();
            post.setPostId(2L);

            Comment savedComment = new Comment();
            savedComment.setCommentId(10L);
            savedComment.setCommentContent("Test comment");
            savedComment.setUser(user);
            savedComment.setPost(post);
            savedComment.setCreatedAt(LocalDateTime.now());
            savedComment.setUpdatedAt(LocalDateTime.now());

            given(userRepository.findById(1L)).willReturn(Optional.of(user));
            given(postRepository.findById(2L)).willReturn(Optional.of(post));
            given(commentRepository.save(any(Comment.class))).willReturn(savedComment);

            // when
            CommentDto result = commentService.createComment(commentDto);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getCommentId()).isEqualTo(10L);
            assertThat(result.getCommentContent()).isEqualTo("Test comment");
            assertThat(result.getAuthorNickname()).isEqualTo("Author");
        }

        @Test
        void 댓글_생성_실패_존재하지않는_사용자() {
            // given
            CommentDto commentDto = new CommentDto();
            commentDto.setUserId(1L);
            commentDto.setPostId(2L);

            given(userRepository.findById(1L)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> commentService.createComment(commentDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("존재하지 않는 사용자입니다.");
        }

        @Test
        void 댓글_생성_실패_존재하지않는_게시글() {
            // given
            CommentDto commentDto = new CommentDto();
            commentDto.setUserId(1L);
            commentDto.setPostId(2L);

            User user = new User();
            user.setUserId(1L);

            given(userRepository.findById(1L)).willReturn(Optional.of(user));
            given(postRepository.findById(2L)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> commentService.createComment(commentDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("존재하지 않는 게시글입니다.");
        }
    }

    @Nested
    class UpdateComment {

        @Test
        void 댓글_수정_성공() {
            // given
            Long commentId = 10L;
            CommentDto commentDto = new CommentDto();
            commentDto.setCommentContent("Updated comment");

            User user = new User();
            user.setUserId(1L);
            user.setNickName("Author");

            Post post = new Post();
            post.setPostId(2L);

            Comment existingComment = new Comment();
            existingComment.setCommentId(10L);
            existingComment.setCommentContent("Old comment");
            existingComment.setUser(user); // User 설정
            existingComment.setPost(post); // Post 설정

            Comment updatedComment = new Comment();
            updatedComment.setCommentId(10L);
            updatedComment.setCommentContent("Updated comment");
            updatedComment.setUser(user); // User 설정
            updatedComment.setPost(post); // Post 설정
            updatedComment.setUpdatedAt(LocalDateTime.now());

            given(commentRepository.findById(commentId)).willReturn(Optional.of(existingComment));
            given(commentRepository.save(any(Comment.class))).willReturn(updatedComment);

            // when
            CommentDto result = commentService.updateComment(commentId, commentDto);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getCommentContent()).isEqualTo("Updated comment");
            assertThat(result.getUserId()).isEqualTo(1L);
        }
    }

    @Nested
    class DeleteComment {

        @Test
        void 댓글_삭제_성공() {
            // given
            Long commentId = 10L;

            willDoNothing().given(commentRepository).deleteById(commentId);

            // when
            commentService.deleteComment(commentId);

            // then
            then(commentRepository).should(times(1)).deleteById(commentId);
        }
    }



    @Nested
    class GetCommentsByPostId {

        @Test
        void 게시글별_댓글_조회() {
            // given
            Long postId = 2L;

            User user1 = new User();
            user1.setUserId(1L);
            user1.setNickName("Author1");

            User user2 = new User();
            user2.setUserId(2L);
            user2.setNickName("Author2");

            Post post = new Post();
            post.setPostId(postId);

            Comment comment1 = new Comment();
            comment1.setCommentId(1L);
            comment1.setCommentContent("Comment 1");
            comment1.setUser(user1); // User 설정
            comment1.setPost(post); // Post 설정

            Comment comment2 = new Comment();
            comment2.setCommentId(2L);
            comment2.setCommentContent("Comment 2");
            comment2.setUser(user2); // User 설정
            comment2.setPost(post); // Post 설정

            given(commentRepository.findByPost_PostId(postId)).willReturn(List.of(comment1, comment2));

            // when
            List<CommentDto> result = commentService.getCommentsByPostId(postId);

            // then
            assertThat(result).hasSize(2);
            assertThat(result.get(0).getCommentContent()).isEqualTo("Comment 1");
            assertThat(result.get(0).getAuthorNickname()).isEqualTo("Author1");
            assertThat(result.get(1).getCommentContent()).isEqualTo("Comment 2");
            assertThat(result.get(1).getAuthorNickname()).isEqualTo("Author2");
        }
    }
}