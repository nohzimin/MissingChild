package com.nohnari.missingchild.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecretPostCreateRequestDto {
    private String title;   // 제목
    private String content; // 내용
}
