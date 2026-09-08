package com.skillsphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "employee_skills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "proficiency")
    private Integer proficiencyLevel; // e.g. 8 for 8/10

    private Integer experienceYears;

    @Builder.Default
    private Boolean verified = true;

    public EmployeeSkill(Employee employee, Skill skill, Integer proficiencyLevel, Integer experienceYears, Boolean verified) {
        this.employee = employee;
        this.skill = skill;
        this.proficiencyLevel = proficiencyLevel;
        this.experienceYears = experienceYears;
        this.verified = verified;
    }

    public Integer getProficiency() {
        return proficiencyLevel;
    }

    public void setProficiency(Integer proficiency) {
        this.proficiencyLevel = proficiency;
    }
}
