//package com.mycom.myapp.domain.child.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Table(name = "report")
//@Setter
//@Getter
//@ToString
//@Entity
//public class Report {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "report_id", updatable = false)
//    private Integer reportId;
//
//    @Column(name = "reporter_name", nullable = false)
//    private String reporterName;
//
//    @Column(name = "reporter_phone", nullable = false)
//    private String reporterPhone;
//
//    @Column(name = "reporter_email", nullable = false)
//    private String reporterEmail;
//
//    @Column(name = "report_date", nullable = false)
//    private LocalDate reportDate;
//
//    @OneToMany(mappedBy = "report")
//    @ToString.Exclude
//    private List<MissingChild> missingChildren = new ArrayList<>();
//
//}
