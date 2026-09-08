package com.skillsphere.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningPathDTO {
    private UUID pathId;
    private String name;
    private String title;
    private String description;
    private String careerTrack;
    private String targetRole;
    private Integer progress;
    private Boolean active;
    private List<CourseDTO> courses;
}
