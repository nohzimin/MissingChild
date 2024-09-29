package com.mycom.myapp.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Entity
@Setter
@Getter
@ToString
@Table(name = "missing_child")
public class MissingChild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id", updatable = false)
    private Integer childId;

    @Column(name = "child_name", nullable = false)
    private String childName;

    @Column(name = "child_gender", nullable = false)
    private Character childGender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "child_age", nullable = false)
    private Integer childAge;

    @Column(name = "last_known_location")
    private String lastKnownLocation;

    @Column(name = "missing_since")
    private LocalDate missingSince;

    @Column(name = "photo_url")
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    @ToString.Exclude
    private Report report;

}

