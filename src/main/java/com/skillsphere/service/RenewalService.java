package com.skillsphere.service;

import com.skillsphere.config.KafkaCertificationProducer;
import com.skillsphere.dto.RenewalDTO;
import com.skillsphere.entity.Certification;
import com.skillsphere.entity.CertificationRenewal;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.CertificationRenewalRepository;
import com.skillsphere.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RenewalService {

    private final CertificationRepository certificationRepository;
    private final CertificationRenewalRepository renewalRepository;
    private final CertificationAuditService auditService;
    private final KafkaCertificationProducer kafkaProducer;

    public RenewalDTO requestRenewal(UUID certificationId, String requestedBy) {
        Certification cert = certificationRepository.findById(certificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Certification not found: " + certificationId));

        CertificationRenewal renewal = CertificationRenewal.builder()
                .certification(cert)
                .oldExpiry(cert.getExpiry())
                .status(CertificationRenewal.RenewalStatus.REQUESTED)
                .requestedBy(requestedBy)
                .requestedAt(LocalDateTime.now())
                .build();

        CertificationRenewal saved = renewalRepository.save(renewal);

        auditService.log(
                cert.getCertId(),
                cert.getEmployee().getEmpId(),
                "RENEWAL_REQUESTED",
                requestedBy
        );

        kafkaProducer.sendRenewalEvent(
                "Certification renewal requested: "
                        + cert.getName()
                        + " for employee "
                        + cert.getEmployee().getName()
        );

        return toDTO(saved);
    }

    public RenewalDTO approveRenewal(UUID renewalId, LocalDate newExpiry, String approvedBy) {
        CertificationRenewal renewal = renewalRepository.findById(renewalId)
                .orElseThrow(() -> new ResourceNotFoundException("Renewal not found: " + renewalId));

        Certification cert = renewal.getCertification();
        cert.setExpiry(newExpiry);
        cert.setStatus(Certification.Status.VALID);
        certificationRepository.save(cert);

        renewal.setNewExpiry(newExpiry);
        renewal.setApprovedBy(approvedBy);
        renewal.setApprovedAt(LocalDateTime.now());
        renewal.setStatus(CertificationRenewal.RenewalStatus.APPROVED);

        CertificationRenewal saved = renewalRepository.save(renewal);

        auditService.log(
                cert.getCertId(),
                cert.getEmployee().getEmpId(),
                "RENEWED",
                approvedBy
        );

        return toDTO(saved);
    }

    private RenewalDTO toDTO(CertificationRenewal renewal) {
        return RenewalDTO.builder()
                .renewalId(renewal.getRenewalId())
                .certificationId(renewal.getCertification().getCertId())
                .oldExpiry(renewal.getOldExpiry())
                .newExpiry(renewal.getNewExpiry())
                .status(renewal.getStatus().name())
                .requestedBy(renewal.getRequestedBy())
                .approvedBy(renewal.getApprovedBy())
                .build();
    }
}
