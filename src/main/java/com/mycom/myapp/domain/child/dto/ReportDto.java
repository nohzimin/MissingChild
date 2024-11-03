package com.mycom.myapp.domain.child.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReportDto {

    private Integer reporterId;
    private Integer childId;

    private String reporterName;

    private String reporterPhone;

    private String reporterEmail;

    private LocalDate reportDate;
}
