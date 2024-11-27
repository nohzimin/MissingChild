package com.nohnari.missingchild.domain.post.domain;

import com.nohnari.missingchild.domain.comment.entity.Comment;
import com.nohnari.missingchild.domain.post.entity.Post;
import com.nohnari.missingchild.domain.user.entity.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Nested
    class Constructor {

        @Nested
        class Success {

            @Test
            void 게시글_객체_생성_성공() {
                // given
                User user = new User();
                user.setUserId(1L);
                user.setNickName("TestUser");

                Comment comment1 = new Comment();
                comment1.setCommentId(1L);
                comment1.setCommentContent("Comment 1");

                Comment comment2 = new Comment();
                comment2.setCommentId(2L);
                comment2.setCommentContent("Comment 2");

                List<Comment> comments = List.of(comment1, comment2);

                // when
                Post post = new Post();
                post.setPostId(1L);
                post.setTitle("Test Title");
                post.setContent("Test Content");
                post.setUser(user);
                post.setComments(comments);
                post.setCreatedAt(LocalDateTime.now());
                post.setUpdatedAt(LocalDateTime.now());

                // then
                assertThat(post).isNotNull();
                assertThat(post.getPostId()).isEqualTo(1L);
                assertThat(post.getTitle()).isEqualTo("Test Title");
                assertThat(post.getContent()).isEqualTo("Test Content");
                assertThat(post.getUser()).isNotNull();
                assertThat(post.getUser().getNickName()).isEqualTo("TestUser");
                assertThat(post.getComments()).hasSize(2);
                assertThat(post.getComments().get(0).getCommentContent()).isEqualTo("Comment 1");
                assertThat(post.getComments().get(1).getCommentContent()).isEqualTo("Comment 2");
            }
        }
    }
}