package com.skillsphere.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private UUID enrollmentId;
    private UUID empId;
    private UUID courseId;
    private String courseTitle;
    private LocalDateTime enrolledAt;
    private Integer progress;
    private Boolean completed;
    private Float score;
    private LocalDateTime completedAt;
}
