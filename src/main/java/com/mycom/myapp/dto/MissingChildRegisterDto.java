package com.mycom.myapp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MissingChildRegisterDto {
    private Integer registerId;
    private Integer childId;
    private String childName;
    private Character childGender;
    private LocalDate dateOfBirth;
    private Integer childAge;
    private String lastKnownLocation;
    private LocalDate missingSince;
    private String photoUrl;
}
