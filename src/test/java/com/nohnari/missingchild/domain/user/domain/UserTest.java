package com.nohnari.missingchild.domain.user.domain;

import com.nohnari.missingchild.domain.child.entity.MissingChild;
import com.nohnari.missingchild.domain.comment.entity.Comment;
import com.nohnari.missingchild.domain.post.entity.Post;
import com.nohnari.missingchild.domain.user.entity.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Nested
    class User_생성 {

        @Test
        void 사용자_객체_생성_성공() {
            // given
            String email = "test@example.com";
            String password = "password123";
            String name = "홍길동";
            String nickName = "길동이";
            String phone = "010-1234-5678";
            String role = "USER";
            LocalDateTime createdAt = LocalDateTime.now();
            LocalDateTime updatedAt = LocalDateTime.now();

            // when
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setName(name);
            user.setNickName(nickName);
            user.setPhone(phone);
            user.setRole(role);
            user.setCreatedAt(createdAt);
            user.setUpdatedAt(updatedAt);
            user.setMissingChildList(new ArrayList<>());
            user.setPostList(new ArrayList<>());
            user.setCommentList(new ArrayList<>());

            // then
            assertThat(user.getEmail()).isEqualTo(email);
            assertThat(user.getPassword()).isEqualTo(password);
            assertThat(user.getName()).isEqualTo(name);
            assertThat(user.getNickName()).isEqualTo(nickName);
            assertThat(user.getPhone()).isEqualTo(phone);
            assertThat(user.getRole()).isEqualTo(role);
            assertThat(user.getCreatedAt()).isEqualTo(createdAt);
            assertThat(user.getUpdatedAt()).isEqualTo(updatedAt);
            assertThat(user.getMissingChildList()).isEmpty();
            assertThat(user.getPostList()).isEmpty();
            assertThat(user.getCommentList()).isEmpty();
        }
    }
}