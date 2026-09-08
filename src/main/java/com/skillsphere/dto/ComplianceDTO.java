package com.skillsphere.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceDTO {
    private String employeeName;
    private long totalCertifications;
    private long validCertifications;
    private long expiredCertifications;
    private boolean compliant;
}
