package com.nohnari.missingchild.domain.post.domain;

import com.nohnari.missingchild.domain.admin.entity.Admin;
import com.nohnari.missingchild.domain.post.entity.SecretPost;
import com.nohnari.missingchild.domain.user.entity.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class SecretPostTest {

    @Nested
    class Constructor {

        @Nested
        class Success {

            @Test
            void SecretPost_객체_생성() {
                // given
                User user = new User();
                user.setUserId(1L);

                Admin admin = new Admin();
                admin.setAdminId(1L);

                String title = "비밀 게시글 제목";
                String content = "비밀 게시글 내용";

                // when
                SecretPost secretPost = new SecretPost();
                secretPost.setTitle(title);
                secretPost.setContent(content);
                secretPost.setUser(user);
                secretPost.setAdmin(admin);
                secretPost.setStatus("미완료");
                secretPost.setCreatedAt(LocalDateTime.now());
                secretPost.setUpdatedAt(LocalDateTime.now());

                // then
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(secretPost.getTitle()).isEqualTo(title);
                    softly.assertThat(secretPost.getContent()).isEqualTo(content);
                    softly.assertThat(secretPost.getUser()).isEqualTo(user);
                    softly.assertThat(secretPost.getAdmin()).isEqualTo(admin);
                    softly.assertThat(secretPost.getStatus()).isEqualTo("미완료");
                    softly.assertThat(secretPost.getCreatedAt()).isNotNull();
                    softly.assertThat(secretPost.getUpdatedAt()).isNotNull();
                });
            }
        }
    }
}