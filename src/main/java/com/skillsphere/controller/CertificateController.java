package com.skillsphere.controller;

import com.skillsphere.entity.LearningCertificate;
import com.skillsphere.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/learning/certificates")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping("/{enrollmentId}")
    public ResponseEntity<LearningCertificate> generateCertificate(@PathVariable UUID enrollmentId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(certificateService.generateCertificate(enrollmentId));
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<LearningCertificate>> getEmployeeCertificates(@PathVariable UUID empId) {
        return ResponseEntity.ok(certificateService.getCertificatesByEmployeeId(empId));
    }
}
