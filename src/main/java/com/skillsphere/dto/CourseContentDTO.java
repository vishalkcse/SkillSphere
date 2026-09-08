package com.skillsphere.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseContentDTO {
    private UUID contentId;
    private UUID courseId;
    private String title;
    private String type;
    private String contentUrl;
    private Integer duration;
    private Integer sequenceOrder;
}
