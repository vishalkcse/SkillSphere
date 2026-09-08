package com.skillsphere.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningCertificateDTO {
    private UUID certificateId;
    private UUID empId;
    private UUID courseId;
    private String courseName;
    private Float score;
    private LocalDate issuedDate;
    private String certificateNumber;
}
