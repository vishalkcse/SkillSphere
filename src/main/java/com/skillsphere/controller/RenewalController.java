package com.skillsphere.controller;

import com.skillsphere.dto.RenewalDTO;
import com.skillsphere.service.RenewalService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/certifications/renewals")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RenewalController {

    private final RenewalService renewalService;

    @PostMapping("/{certificationId}")
    public ResponseEntity<RenewalDTO> request(
            @PathVariable UUID certificationId,
            @RequestParam String requestedBy) {
        return ResponseEntity.status(HttpStatus.CREATED).body(renewalService.requestRenewal(certificationId, requestedBy));
    }

    @PutMapping("/{renewalId}/approve")
    public ResponseEntity<RenewalDTO> approve(
            @PathVariable UUID renewalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate newExpiry,
            @RequestParam String approvedBy) {
        return ResponseEntity.ok(renewalService.approveRenewal(renewalId, newExpiry, approvedBy));
    }
}
