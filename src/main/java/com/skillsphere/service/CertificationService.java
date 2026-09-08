package com.skillsphere.service;

import com.skillsphere.dto.CertificationDTO;
import com.skillsphere.entity.Certification;
import com.skillsphere.entity.Employee;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.CertificationRepository;
import com.skillsphere.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final EmployeeRepository employeeRepository;

    public CertificationDTO register(CertificationDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + dto.getEmpId()));

        Certification certification = Certification.builder()
                .employee(employee)
                .name(dto.getName())
                .issuingOrganization(dto.getIssuingOrganization() != null ? dto.getIssuingOrganization() : "AWS / Oracle")
                .credentialId(dto.getCredentialId())
                .issued(dto.getIssued() != null ? dto.getIssued() : LocalDate.now().minusYears(1))
                .expiry(dto.getExpiry() != null ? dto.getExpiry() : LocalDate.now().plusYears(2))
                .status(calculateStatus(dto.getExpiry()))
                .build();

        Certification saved = certificationRepository.save(certification);
        return toDTO(saved);
    }

    public CertificationDTO getById(UUID id) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certification not found: " + id));
        return toDTO(certification);
    }

    public List<CertificationDTO> getByEmployee(UUID empId) {
        return certificationRepository.findByEmployeeEmpId(empId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public CertificationDTO update(UUID id, CertificationDTO dto) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certification not found: " + id));

        certification.setName(dto.getName());
        certification.setIssuingOrganization(dto.getIssuingOrganization());
        certification.setCredentialId(dto.getCredentialId());
        certification.setIssued(dto.getIssued());
        certification.setExpiry(dto.getExpiry());
        certification.setStatus(calculateStatus(dto.getExpiry()));

        return toDTO(certificationRepository.save(certification));
    }

    public void delete(UUID id) {
        if (!certificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Certification not found: " + id);
        }
        certificationRepository.deleteById(id);
    }

    public List<CertificationDTO> getExpiring() {
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(30);

        return certificationRepository.findByExpiryBetween(today, end)
                .stream()
                .map(cert -> {
                    cert.setStatus(Certification.Status.PENDING_RENEWAL);
                    certificationRepository.save(cert);
                    return toDTO(cert);
                })
                .toList();
    }

    public List<CertificationDTO> getExpired() {
        return certificationRepository.findByStatus(Certification.Status.EXPIRED)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public void refreshStatus(Certification certification) {
        certification.setStatus(calculateStatus(certification.getExpiry()));
        certificationRepository.save(certification);
    }

    public Certification.Status calculateStatus(LocalDate expiry) {
        LocalDate today = LocalDate.now();
        if (expiry == null || expiry.isBefore(today)) {
            return Certification.Status.EXPIRED;
        }
        if (!expiry.isAfter(today.plusDays(30))) {
            return Certification.Status.PENDING_RENEWAL;
        }
        return Certification.Status.VALID;
    }

    public CertificationDTO toDTO(Certification certification) {
        return CertificationDTO.builder()
                .certId(certification.getCertId())
                .empId(certification.getEmployee() != null ? certification.getEmployee().getEmpId() : null)
                .employeeName(certification.getEmployee() != null ? certification.getEmployee().getName() : null)
                .name(certification.getName())
                .issuingOrganization(certification.getIssuingOrganization())
                .credentialId(certification.getCredentialId())
                .issued(certification.getIssued())
                .expiry(certification.getExpiry())
                .status(certification.getStatus() != null ? certification.getStatus().name() : "VALID")
                .build();
    }
}
