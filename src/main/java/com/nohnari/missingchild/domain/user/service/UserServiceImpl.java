package com.nohnari.missingchild.domain.user.service;

import com.nohnari.missingchild.domain.user.dto.SignupRequestDto;
import com.nohnari.missingchild.domain.user.dto.SignupResultDto;
import com.nohnari.missingchild.domain.user.dto.UserDto;
import com.nohnari.missingchild.domain.user.dto.UserResultDto;
import com.nohnari.missingchild.domain.user.entity.User;
import com.nohnari.missingchild.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Long getUserId(String email) {
        User user = userRepository.findByEmail(email);
        Long userId = user.getUserId();
        return userId;

    }

    // 회원가입
    @Override
    public SignupResultDto signup(SignupRequestDto signupRequestDto) {
        SignupResultDto signupResultDto = new SignupResultDto();

        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();
        String name = signupRequestDto.getName();
        String nickName = signupRequestDto.getNickName();
        String phone = signupRequestDto.getPhone();

        // 이메일 중복 확인
        Boolean isExist = userRepository.existsByEmail(email);
        if(isExist) {
            LOGGER.info("[SIGNUP] 이메일 중복");
            signupResultDto.setStatus("이메일 중복입니다.");
            return signupResultDto;
        }

        // 회원가입 진행
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setName(name);
        user.setNickName(nickName);
        user.setPhone(phone);
        user.setCreatedAt(LocalDateTime.now()); // 회원가입 시각 자동 저장
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole("ROLE_USER");

       User savedUser = userRepository.save(user);

        if (savedUser == null || savedUser.getEmail() == null || savedUser.getEmail().isEmpty()) {
            LOGGER.info("[SIGNUP] 회원가입 실패");
            signupResultDto.setStatus("회원가입 실패");
        } else {
            LOGGER.info("[SIGNUP] 회원가입 성공");
            signupResultDto.setStatus("회원가입 성공");
        }

        return signupResultDto;
    }

    // 유저페이지 회원 정보 조회
    @Override
    public UserResultDto detailUserPage(String email) {
        UserResultDto userResultDto = new UserResultDto();

        User user = userRepository.findByEmail(email);

        UserDto userDto = new UserDto();

        userDto.setUserId(user.getUserId());
        userDto.setName(user.getName());
        userDto.setNickName(user.getNickName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());

        userResultDto.setUserDto(userDto);
        userResultDto.setResult("success");

        return userResultDto;
    }

    // 유저페이지 회원 정보 수정
    @Override
    public UserResultDto updateUserPage(UserDto userDto) {
        UserResultDto userResultDto = new UserResultDto();

        try {

            Optional<User> optionalUser = userRepository.findById(userDto.getUserId());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                // 비밀번호 , 전화번호, 닉네임 수정
                if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                    String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
                    user.setPassword(encodedPassword);
                }
                user.setNickName(userDto.getNickName());
                user.setPhone(userDto.getPhone());

                userRepository.save(user);
                userResultDto.setResult("success");

            } else {
                userResultDto.setResult("[UserUPDATE] fail");
            }

        }
            catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("[UserUPDATE] fail");
        }

        return userResultDto;
    }





}
