package com.skillsphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "skills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "skill_id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String skillName;

    private String level;

    private String description;

    private String category; // TECHNICAL, DOMAIN, SOFT

    @Builder.Default
    private Boolean isActive = true;

    public enum Category {
        TECHNICAL, DOMAIN, SOFT
    }

    public Skill(String skillName, String level, String description, String category) {
        this.skillName = skillName;
        this.level = level;
        this.description = description;
        this.category = category;
        this.isActive = true;
    }

    public UUID getSkillId() {
        return id;
    }

    public void setSkillId(UUID skillId) {
        this.id = skillId;
    }

    public String getName() {
        return skillName;
    }

    public void setName(String name) {
        this.skillName = name;
    }
}
