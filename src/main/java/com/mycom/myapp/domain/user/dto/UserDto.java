package com.mycom.myapp.domain.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phone;
}
