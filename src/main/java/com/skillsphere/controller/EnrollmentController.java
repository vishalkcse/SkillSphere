package com.skillsphere.controller;

import com.skillsphere.dto.EnrollmentDTO;
import com.skillsphere.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/learning/enrollments")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public EnrollmentDTO enroll(
            @RequestParam UUID empId,
            @RequestParam UUID courseId) {
        return enrollmentService.enroll(empId, courseId);
    }

    @GetMapping("/employee/{empId}")
    public List<EnrollmentDTO> getEmployeeEnrollments(
            @PathVariable UUID empId) {
        return enrollmentService.getEmployeeEnrollments(empId);
    }

    @PutMapping("/{id}/progress")
    public EnrollmentDTO updateProgress(
            @PathVariable UUID id,
            @RequestParam Integer progress,
            @RequestParam(required = false) Float score) {
        return enrollmentService.updateProgress(id, progress, score);
    }
}
