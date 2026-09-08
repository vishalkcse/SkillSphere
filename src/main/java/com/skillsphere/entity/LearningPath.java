package com.skillsphere.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "learning_paths")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningPath {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pathId;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String careerTrack;

    @Builder.Default
    private Integer progress = 0;

    @Builder.Default
    private Boolean active = true;

    private String targetRole;

    public String getTitle() {
        return name != null ? name : targetRole;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public static class LearningPathBuilder {
        public LearningPathBuilder title(String title) {
            this.name = title;
            return this;
        }
    }
}
