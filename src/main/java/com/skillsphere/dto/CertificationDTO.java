package com.skillsphere.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationDTO {
    private UUID certId;
    private UUID empId;
    private String employeeName;
    private String name;
    private String issuingOrganization;
    private String credentialId;
    private LocalDate issued;
    private LocalDate expiry;
    private String status;
}
