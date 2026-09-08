package com.skillsphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "competency_frameworks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetencyFramework {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String roleTitle;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private Integer requiredProficiency;

    private String department;

    private String targetProficiencySummary;

    private Integer minRequiredScore;

    public CompetencyFramework(String roleTitle, String department, String targetProficiencySummary, Integer minRequiredScore) {
        this.roleTitle = roleTitle;
        this.department = department;
        this.targetProficiencySummary = targetProficiencySummary;
        this.minRequiredScore = minRequiredScore;
    }

    public CompetencyFramework(String roleTitle, Skill skill, Integer requiredProficiency, String department) {
        this.roleTitle = roleTitle;
        this.skill = skill;
        this.requiredProficiency = requiredProficiency;
        this.department = department;
        this.targetProficiencySummary = skill != null ? "Requires " + skill.getName() + " (" + requiredProficiency + "/10)" : null;
        this.minRequiredScore = requiredProficiency != null ? requiredProficiency * 10 : null;
    }
}
