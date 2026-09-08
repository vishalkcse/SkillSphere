package com.skillsphere.controller;

import com.skillsphere.dto.CertificationDTO;
import com.skillsphere.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/certifications")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping
    public ResponseEntity<CertificationDTO> register(@RequestBody CertificationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(certificationService.register(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(certificationService.getById(id));
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<CertificationDTO>> getByEmployee(@PathVariable UUID empId) {
        return ResponseEntity.ok(certificationService.getByEmployee(empId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificationDTO> update(@PathVariable UUID id, @RequestBody CertificationDTO dto) {
        return ResponseEntity.ok(certificationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        certificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/expiring")
    public ResponseEntity<List<CertificationDTO>> expiring() {
        return ResponseEntity.ok(certificationService.getExpiring());
    }

    @GetMapping("/expired")
    public ResponseEntity<List<CertificationDTO>> expired() {
        return ResponseEntity.ok(certificationService.getExpired());
    }
}
