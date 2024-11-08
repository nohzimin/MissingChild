package com.mycom.myapp.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String phone;
}
