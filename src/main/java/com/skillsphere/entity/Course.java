package com.skillsphere.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID courseId;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private CourseType type;

    private String instructor;

    private Double rating;

    @Builder.Default
    private Boolean active = true;

    private String category;

    private Integer durationHours;

    private Float completionRate;

    public enum CourseType {
        ONLINE, WORKSHOP, WEBINAR, BOOTCAMP, ONLINE_COURSE
    }

    public Course(String title, String description, CourseType type, String category, Integer durationHours, Float completionRate) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.category = category;
        this.durationHours = durationHours;
        this.duration = durationHours;
        this.completionRate = completionRate;
        this.active = true;
    }

    public Integer getDuration() {
        return duration != null ? duration : durationHours;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
        this.durationHours = duration;
    }
}
