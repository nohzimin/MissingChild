package com.nohnari.missingchild.domain.comment.domain;

import com.nohnari.missingchild.domain.comment.entity.Comment;
import com.nohnari.missingchild.domain.post.entity.Post;
import com.nohnari.missingchild.domain.user.entity.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

class CommentTest {

    @Nested
    class Constructor {

        @Nested
        class Success {

            @Test
            void Comment_객체_생성() {
                // given
                String commentContent = "This is a comment.";
                User user = Mockito.mock(User.class);
                Post post = Mockito.mock(Post.class);
                LocalDateTime createdAt = LocalDateTime.now();
                LocalDateTime updatedAt = LocalDateTime.now();

                // when
                Comment comment = new Comment();
                comment.setCommentContent(commentContent);
                comment.setUser(user);
                comment.setPost(post);
                comment.setCreatedAt(createdAt);
                comment.setUpdatedAt(updatedAt);

                // then
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(comment).isNotNull();
                    softly.assertThat(comment.getCommentContent()).isEqualTo(commentContent);
                    softly.assertThat(comment.getUser()).isNotNull();
                    softly.assertThat(comment.getPost()).isNotNull();
                    softly.assertThat(comment.getCreatedAt()).isEqualTo(createdAt);
                    softly.assertThat(comment.getUpdatedAt()).isEqualTo(updatedAt);
                });
            }
        }
    }
}