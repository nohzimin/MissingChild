package com.nohnari.missingchild.domain.user.service;

import com.nohnari.missingchild.domain.user.dto.SignupRequestDto;
import com.nohnari.missingchild.domain.user.dto.SignupResultDto;
import com.nohnari.missingchild.domain.user.dto.UserDto;
import com.nohnari.missingchild.domain.user.dto.UserResultDto;
import com.nohnari.missingchild.domain.user.entity.User;
import com.nohnari.missingchild.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;


class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock User
        mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setName("Test User");
        mockUser.setNickName("Tester");
        mockUser.setPhone("1234567890");
    }

    @Nested
    class GetUserId {

        @Test
        void 사용자_ID_조회_성공() {
            // given
            given(userRepository.findByEmail("test@example.com")).willReturn(mockUser);

            // when
            Long userId = userService.getUserId("test@example.com");

            // then
            assertThat(userId).isEqualTo(1L);
            verify(userRepository).findByEmail("test@example.com");
        }
    }

    @Nested
    class Signup {

        @Test
        void 회원가입_성공() {
            // given
            SignupRequestDto requestDto = new SignupRequestDto(
                    "test@example.com",
                    "password123",
                    "Test User",
                    "Tester",
                    "1234567890"
            );

            given(userRepository.existsByEmail("test@example.com")).willReturn(false); // 중복 이메일 확인
            given(passwordEncoder.encode("password123")).willReturn("encodedPassword"); // 비밀번호 암호화

            // Mock된 User 저장 결과 설정
            User savedMockUser = new User();
            savedMockUser.setUserId(1L);
            savedMockUser.setEmail("test@example.com");
            savedMockUser.setName("Test User");
            savedMockUser.setNickName("Tester");
            savedMockUser.setPhone("1234567890");
            savedMockUser.setPassword("encodedPassword");
            savedMockUser.setRole("ROLE_USER");

            given(userRepository.save(any(User.class))).willReturn(savedMockUser); // 저장된 User 반환

            // when
            SignupResultDto resultDto = userService.signup(requestDto);

            // then
            assertThat(resultDto.getStatus()).isEqualTo("회원가입 성공");
            verify(userRepository).existsByEmail("test@example.com");
            verify(userRepository).save(any(User.class));
        }

        @Test
        void 회원가입_실패_이메일_중복() {
            // given
            SignupRequestDto requestDto = new SignupRequestDto(
                    "test@example.com",
                    "password123",
                    "Test User",
                    "Tester",
                    "1234567890"
            );
            given(userRepository.existsByEmail("test@example.com")).willReturn(true);

            // when
            SignupResultDto resultDto = userService.signup(requestDto);

            // then
            assertThat(resultDto.getStatus()).isEqualTo("이메일 중복입니다.");
            verify(userRepository).existsByEmail("test@example.com");
        }
    }

    @Nested
    class DetailUserPage {

        @Test
        void 유저_정보_조회_성공() {
            // given
            given(userRepository.findByEmail("test@example.com")).willReturn(mockUser);

            // when
            UserResultDto resultDto = userService.detailUserPage("test@example.com");

            // then
            assertThat(resultDto.getResult()).isEqualTo("success");
            assertThat(resultDto.getUserDto().getEmail()).isEqualTo("test@example.com");
            verify(userRepository).findByEmail("test@example.com");
        }
    }

    @Nested
    class UpdateUserPage {

        @Test
        void 유저_정보_수정_성공() {
            // given
            UserDto userDto = new UserDto();
            userDto.setUserId(1L);
            userDto.setPassword("newPassword");
            userDto.setNickName("UpdatedTester");
            userDto.setPhone("0987654321");

            given(userRepository.findById(1L)).willReturn(Optional.of(mockUser));
            given(passwordEncoder.encode("newPassword")).willReturn("encodedNewPassword");

            // when
            UserResultDto resultDto = userService.updateUserPage(userDto);

            // then
            assertThat(resultDto.getResult()).isEqualTo("success");
            assertThat(mockUser.getNickName()).isEqualTo("UpdatedTester");
            assertThat(mockUser.getPhone()).isEqualTo("0987654321");
            verify(userRepository).save(mockUser);
        }

        @Test
        void 유저_정보_수정_실패_존재하지않는_유저() {
            // given
            UserDto userDto = new UserDto();
            userDto.setUserId(999L);

            given(userRepository.findById(999L)).willReturn(Optional.empty());

            // when
            UserResultDto resultDto = userService.updateUserPage(userDto);

            // then
            assertThat(resultDto.getResult()).isEqualTo("[UserUPDATE] fail");
        }
    }
}