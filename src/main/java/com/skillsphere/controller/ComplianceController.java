package com.skillsphere.controller;

import com.skillsphere.dto.ComplianceDTO;
import com.skillsphere.service.ComplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/certifications/compliance")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ComplianceController {

    private final ComplianceService complianceService;

    @GetMapping("/{empId}")
    public ResponseEntity<ComplianceDTO> getCompliance(@PathVariable UUID empId) {
        return ResponseEntity.ok(complianceService.getCompliance(empId));
    }
}
