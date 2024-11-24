package com.mycom.myapp.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecretPostAnswerRequestDto {
    private String answer; // 답변 내용
}
