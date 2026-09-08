package com.skillsphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "assessments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "assess_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = true)
    private Skill skill;

    private String title;

    private Float score;

    private Boolean passed;

    private LocalDate assessmentDate;

    @Builder.Default
    private Boolean verified = false;

    public Assessment(Employee employee, String title, Float score, LocalDate assessmentDate, Boolean verified) {
        this.employee = employee;
        this.title = title;
        this.score = score;
        this.assessmentDate = assessmentDate;
        this.verified = verified != null ? verified : false;
        this.passed = score != null && score >= 70.0f;
    }

    public Assessment(Employee employee, String title, Double score, LocalDate assessmentDate, Boolean verified) {
        this(employee, title, score != null ? score.floatValue() : null, assessmentDate, verified);
    }

    public UUID getAssessId() {
        return id;
    }

    public void setAssessId(UUID assessId) {
        this.id = assessId;
    }

    public UUID getAssessmentId() {
        return id;
    }

    public void setAssessmentId(UUID assessmentId) {
        this.id = assessmentId;
    }

    public Boolean getPassed() {
        return passed != null ? passed : (score != null && score >= 70.0f);
    }

    public Boolean getVerified() {
        return verified != null ? verified : false;
    }
}
