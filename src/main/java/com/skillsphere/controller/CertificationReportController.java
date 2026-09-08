package com.skillsphere.controller;

import com.skillsphere.dto.CertificationReportDTO;
import com.skillsphere.service.CertificationReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certifications/report")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CertificationReportController {

    private final CertificationReportService reportService;

    @GetMapping
    public ResponseEntity<CertificationReportDTO> report() {
        return ResponseEntity.ok(reportService.generate());
    }
}
