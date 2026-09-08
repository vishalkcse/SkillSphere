package com.skillsphere.service;

import com.skillsphere.dto.ComplianceDTO;
import com.skillsphere.entity.Certification;
import com.skillsphere.entity.Employee;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.CertificationRepository;
import com.skillsphere.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplianceService {

    private final EmployeeRepository employeeRepository;
    private final CertificationRepository certificationRepository;

    public ComplianceDTO getCompliance(UUID empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + empId));

        List<Certification> certifications = certificationRepository.findByEmployeeEmpId(empId);

        long total = certifications.size();
        long expired = certifications.stream()
                .filter(c -> c.getStatus() == Certification.Status.EXPIRED)
                .count();

        long valid = certifications.stream()
                .filter(c -> c.getStatus() == Certification.Status.VALID)
                .count();

        return ComplianceDTO.builder()
                .employeeName(employee.getName())
                .totalCertifications(total)
                .validCertifications(valid)
                .expiredCertifications(expired)
                .compliant(total > 0 && expired == 0)
                .build();
    }
}
