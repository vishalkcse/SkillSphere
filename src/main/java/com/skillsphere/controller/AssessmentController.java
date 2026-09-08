package com.skillsphere.controller;

import com.skillsphere.dto.AssessmentDTO;
import com.skillsphere.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/assessments", "/api/v1/assessments"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping
    public ResponseEntity<AssessmentDTO> createAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assessmentService.createAssessment(assessmentDTO));
    }

    @PutMapping("/{id}/verify")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<AssessmentDTO> verifyAssessment(@PathVariable UUID id) {
        return ResponseEntity.ok(assessmentService.verifyAssessment(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentDTO> getAssessmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(assessmentService.getAssessmentById(id));
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<AssessmentDTO>> getAssessmentsByEmployeeId(@PathVariable UUID empId) {
        return ResponseEntity.ok(assessmentService.getAssessmentsByEmployeeId(empId));
    }

    @GetMapping
    public ResponseEntity<List<AssessmentDTO>> getAllAssessments() {
        return ResponseEntity.ok(assessmentService.getAllAssessments());
    }
}
