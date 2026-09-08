package com.skillsphere.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenewalDTO {
    private UUID renewalId;
    private UUID certificationId;
    private LocalDate oldExpiry;
    private LocalDate newExpiry;
    private String status;
    private String requestedBy;
    private String approvedBy;
}
