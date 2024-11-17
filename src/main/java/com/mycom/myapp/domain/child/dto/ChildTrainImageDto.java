package com.mycom.myapp.domain.child.dto;

import com.mycom.myapp.domain.child.entity.MissingChild;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor // 모든 필드를 포함하는 생성자 추가
public class ChildTrainImageDto {
    private Long imageId;
    private String imageUrl;
    private Integer childId;
}