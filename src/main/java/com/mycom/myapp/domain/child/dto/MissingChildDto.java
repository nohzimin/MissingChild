package com.mycom.myapp.domain.child.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MissingChildDto {

    private Integer childId;
    private String childName;
    private Character childGender;
    private LocalDate dateOfBirth;
    private Integer childAge;
    private String lastKnownLocation;
    private LocalDate missingSince;
    private String photoUrl;

//    private List<ReportDto> reportDtoList = new ArrayList<>();

}