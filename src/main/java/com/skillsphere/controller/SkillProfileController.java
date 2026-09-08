package com.skillsphere.controller;

import com.skillsphere.entity.*;
import com.skillsphere.repository.*;
import com.skillsphere.service.SkillProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping({"/api/skills", "/api/v1"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SkillProfileController {

    private final SkillProfileService skillProfileService;
    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;
    private final CertificationRepository certificationRepository;
    private final AssessmentRepository assessmentRepository;
    private final CompetencyFrameworkRepository competencyFrameworkRepository;

    @GetMapping("/employee/{empId}")
    public ResponseEntity<Object> getProfile(@PathVariable UUID empId) {
        return ResponseEntity.ok(skillProfileService.getSkillProfile(empId));
    }

    @GetMapping("/skill-profiles/john-smith")
    public ResponseEntity<Object> getJohnSmithProfile() {
        Employee john = skillProfileService.getJohnSmith();
        return ResponseEntity.ok(skillProfileService.getSkillProfile(john.getId()));
    }

    @GetMapping("/skill-profiles/{empId}")
    public ResponseEntity<Object> getSkillProfile(@PathVariable UUID empId) {
        return ResponseEntity.ok(skillProfileService.getSkillProfile(empId));
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @GetMapping("/certifications")
    public ResponseEntity<List<Certification>> getAllCertifications() {
        return ResponseEntity.ok(certificationRepository.findAll());
    }

    @GetMapping("/assessments")
    public ResponseEntity<List<Assessment>> getAllAssessments() {
        return ResponseEntity.ok(assessmentRepository.findAll());
    }

    @GetMapping("/competencies")
    public ResponseEntity<List<CompetencyFramework>> getAllCompetencies() {
        return ResponseEntity.ok(competencyFrameworkRepository.findAll());
    }

    @PostMapping("/rbac/verify")
    public ResponseEntity<Map<String, Object>> verifyRbacAccess(@RequestParam String role, @RequestParam String action) {
        boolean granted = "HR".equalsIgnoreCase(role) || "ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role);
        return ResponseEntity.ok(Map.of(
                "role", role,
                "actionRequested", action,
                "accessGranted", granted,
                "message", granted ? "Access granted for HR@Service role authorization." : "Access DENIED. HR level authorization required."
        ));
    }
}
