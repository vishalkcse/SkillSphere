package com.skillsphere.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "learning_certificates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID certificateId;

    private UUID empId;

    private UUID courseId;

    private String courseName;

    private Float score;

    private LocalDate issuedDate;

    private String certificateNumber;
}
