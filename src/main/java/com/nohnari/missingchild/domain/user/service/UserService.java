package com.nohnari.missingchild.domain.user.service;


import com.nohnari.missingchild.domain.user.dto.SignupRequestDto;
import com.nohnari.missingchild.domain.user.dto.SignupResultDto;
import com.nohnari.missingchild.domain.user.dto.UserDto;
import com.nohnari.missingchild.domain.user.dto.UserResultDto;

public interface UserService {

    public Long getUserId(String email);
    public SignupResultDto signup(SignupRequestDto signupRequestDto);
    public UserResultDto detailUserPage(String email);
    public UserResultDto updateUserPage(UserDto userDto);
}
