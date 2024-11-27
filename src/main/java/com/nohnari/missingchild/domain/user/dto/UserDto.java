package com.nohnari.missingchild.domain.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String phone;
}
