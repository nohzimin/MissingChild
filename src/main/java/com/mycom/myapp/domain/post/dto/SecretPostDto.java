package com.mycom.myapp.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecretPostDto {
    private Long secretPostId;  // 게시글 ID
    private String title;       // 제목
    private String content;     // 내용
    private String status;      // 답변 상태
    private String answer;      // 답변 내용 (관리자만 입력 가능)
    private String createdAt;   // 생성 시간
    private String updatedAt;   // 수정 시간
    private String authorName;  // 작성자 이름
}