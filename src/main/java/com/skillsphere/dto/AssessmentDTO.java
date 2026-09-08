package com.skillsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentDTO {
    private UUID assessId;
    private UUID empId;
    private UUID skillId;
    private String title;
    private Float score;
    private Boolean passed;
    private Boolean verified;
    private LocalDate assessmentDate;

    public UUID getAssessmentId() {
        return assessId;
    }

    public void setAssessmentId(UUID assessmentId) {
        this.assessId = assessmentId;
    }

    public UUID getEmployeeId() {
        return empId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.empId = employeeId;
    }
}
