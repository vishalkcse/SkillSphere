package com.skillsphere.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "internal_jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID jobId;

    private String title;

    private String department;

    private String requiredSkills;

    private Integer minimumExperience;

    @Builder.Default
    private Boolean active = true;
}
