package com.mycom.myapp.domain.child.dto;

import com.mycom.myapp.domain.child.entity.MissingChild;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ChildTrainImageDto {
    private Long imageId;
    private String imageUrl;
    private Integer childId;
}