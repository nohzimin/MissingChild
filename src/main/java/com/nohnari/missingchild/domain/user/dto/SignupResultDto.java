package com.nohnari.missingchild.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupResultDto {
    private String status;
    private boolean success;
    private int code;
    private String msg;

}

