package com.nohnari.missingchild.domain.admin.service;

import com.nohnari.missingchild.domain.admin.dto.AdminSignupRequestDto;
import com.nohnari.missingchild.domain.admin.dto.AdminSignupResultDto;
import com.nohnari.missingchild.domain.admin.entity.Admin;
import com.nohnari.missingchild.domain.admin.repository.AdminRepository;
import com.nohnari.missingchild.domain.admin.service.AdminServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Nested
    class Signup {

        @Nested
        class Success {

            @Test
            void 관리자_가입_성공() {
                // given
                AdminSignupRequestDto requestDto = new AdminSignupRequestDto();
                requestDto.setAdminEmail("admin@example.com");
                requestDto.setAdminPassword("securepassword");
                requestDto.setAdminName("Admin User");

                Admin admin = new Admin();
                admin.setAdminId(1L);
                admin.setAdminEmail(requestDto.getAdminEmail());
                admin.setAdminName(requestDto.getAdminName());
                admin.setAdminPassword("encodedPassword");
                admin.setCreatedAt(LocalDateTime.now());
                admin.setUpdatedAt(LocalDateTime.now());
                admin.setRole("ROLE_ADMIN");

                given(adminRepository.existsByAdminEmail(anyString())).willReturn(false);
                given(bCryptPasswordEncoder.encode(anyString())).willReturn("encodedPassword");
                given(adminRepository.save(any(Admin.class))).willReturn(admin);

                // when
                AdminSignupResultDto result = adminService.signup(requestDto);

                // then
                assertSoftly(softly -> {
                    softly.assertThat(result.getStatus()).isEqualTo("관리자 가입 성공");
                });
            }
        }

        @Nested
        class Failure {

            @Test
            void 이메일_중복_가입_실패() {
                // given
                AdminSignupRequestDto requestDto = new AdminSignupRequestDto();
                requestDto.setAdminEmail("admin@example.com");
                requestDto.setAdminPassword("securepassword");
                requestDto.setAdminName("Admin User");

                given(adminRepository.existsByAdminEmail(anyString())).willReturn(true);

                // when
                AdminSignupResultDto result = adminService.signup(requestDto);

                // then
                assertSoftly(softly -> {
                    softly.assertThat(result.getStatus()).isEqualTo("이메일 중복입니다.");
                });
            }
        }
    }

    @Nested
    class GetAdminId {

        @Nested
        class Success {

            @Test
            void 이메일로_관리자_ID_조회_성공() {
                // given
                Admin admin = new Admin();
                admin.setAdminId(1L);
                admin.setAdminEmail("admin@example.com");

                given(adminRepository.findByAdminEmail("admin@example.com")).willReturn(admin);

                // when
                Long adminId = adminService.getAdminId("admin@example.com");

                // then
                assertThat(adminId).isEqualTo(1L);
            }
        }

        @Nested
        class Failure {

            @Test
            void 존재하지_않는_이메일_관리자_ID_조회_실패() {
                // given
                given(adminRepository.findByAdminEmail(anyString())).willReturn(null);

                // when & then
                assertThrows(IllegalArgumentException.class, () -> {
                    adminService.getAdminId("nonexistent@example.com");
                });
            }
        }
    }
}