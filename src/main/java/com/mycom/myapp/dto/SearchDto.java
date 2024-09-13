package com.mycom.myapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private String childName;
    private Integer childAge;
    private Character childGender;
    private String lastKnownLocation;
    private LocalDate missingSince;
}
