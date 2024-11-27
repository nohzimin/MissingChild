package com.nohnari.missingchild.domain.child.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor // 모든 필드를 포함하는 생성자 추가
public class ChildTrainImageDto {
    private Long imageId;
    private String imageUrl;
    private Integer childId;
}