package com.skillsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetencyGapDTO {
    private String skillName;
    private Integer currentProficiency;
    private Integer requiredProficiency;
    private Integer gap; // e.g. 3 for "Gaps: Angular +3"
}
