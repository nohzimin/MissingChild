package com.mycom.myapp.domain.child.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MissingChildRegisterDto {
    private String reporterName;
    private String reporterEmail;
    private String reporterPhone;

    private String childName;
    private Integer childAge;
    private Character childGender;
    private String lastKnownLocation;
    private LocalDate missingSince;
    private String photoUrl; // URL of the uploaded image
}