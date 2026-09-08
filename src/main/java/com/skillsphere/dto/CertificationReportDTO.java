package com.skillsphere.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationReportDTO {
    private long total;
    private long active;
    private long expired;
    private long pendingRenewal;
    private long expiringWithin30Days;
    private double renewalRate;
}
