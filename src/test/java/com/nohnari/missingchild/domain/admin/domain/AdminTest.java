package com.nohnari.missingchild.domain.admin.domain;

import com.nohnari.missingchild.domain.admin.entity.Admin;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class AdminTest {

    @Nested
    class Constructor {

        @Nested
        class success {

            @Test
            void Admin_객체_생성() {
                // given
                String name = "Admin User";
                String email = "admin@example.com";
                String password = "securepassword";
                String role = "ROLE_ADMIN";
                LocalDateTime now = LocalDateTime.now();

                // when
                Admin admin = new Admin();
                admin.setAdminName(name);
                admin.setAdminEmail(email);
                admin.setAdminPassword(password);
                admin.setRole(role);
                admin.setCreatedAt(now);
                admin.setUpdatedAt(now);

                // then
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(admin).isNotNull();
                    softly.assertThat(admin.getAdminName()).isEqualTo(name);
                    softly.assertThat(admin.getAdminEmail()).isEqualTo(email);
                    softly.assertThat(admin.getAdminPassword()).isEqualTo(password);
                    softly.assertThat(admin.getRole()).isEqualTo(role);
                    softly.assertThat(admin.getCreatedAt()).isEqualTo(now);
                    softly.assertThat(admin.getUpdatedAt()).isEqualTo(now);
                });
            }
        }
    }
}