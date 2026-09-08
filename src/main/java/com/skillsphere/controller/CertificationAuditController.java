package com.skillsphere.controller;

import com.skillsphere.entity.CertificationAudit;
import com.skillsphere.service.CertificationAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/certifications")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CertificationAuditController {

    private final CertificationAuditService auditService;

    @GetMapping("/{certificationId}/audit")
    public List<CertificationAudit> getAudit(@PathVariable UUID certificationId) {
        return auditService.getAudit(certificationId);
    }
}
