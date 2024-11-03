package com.mycom.myapp.domain.user.service;


import com.mycom.myapp.domain.user.dto.SignupRequestDto;
import com.mycom.myapp.domain.user.dto.SignupResultDto;
import com.mycom.myapp.domain.user.dto.UserDto;
import com.mycom.myapp.domain.user.dto.UserResultDto;

public interface UserService {

    public Long getUserId(String email);
    public SignupResultDto signup(SignupRequestDto signupRequestDto);
    public UserResultDto detailUserPage(String email);
    public UserResultDto updateUserPage(UserDto userDto);
}
