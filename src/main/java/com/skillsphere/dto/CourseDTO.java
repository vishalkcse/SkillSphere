package com.skillsphere.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private UUID courseId;
    private String title;
    private String description;
    private Integer duration;
    private String type;
    private String instructor;
    private Double rating;
    private Boolean active;
    private String category;
    private Integer durationHours;
    private Float completionRate;
}
