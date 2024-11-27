package com.nohnari.missingchild.domain.child.entity;
import com.nohnari.missingchild.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


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

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt = LocalDateTime.now();  // 기본값 설정

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt = LocalDateTime.now();  // 기본값 설정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "missingChild", cascade = CascadeType.ALL)
    private List<ChildTrainImage> trainImages; // 학습 이미지와 연관

}

