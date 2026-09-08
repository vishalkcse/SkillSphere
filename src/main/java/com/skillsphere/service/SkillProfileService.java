package com.skillsphere.service;

import com.skillsphere.entity.*;
import com.skillsphere.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.skillsphere.exception.ResourceNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillProfileService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeSkillRepository employeeSkillRepository;
    private final AssessmentRepository assessmentRepository;
    private final CertificationRepository certificationRepository;
    private final SkillRepository skillRepository;

    @Cacheable(value = "skillProfiles", key = "#empId")
    public Object getSkillProfile(UUID empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + empId));

        List<EmployeeSkill> skills = employeeSkillRepository.findByEmployeeEmpId(empId);
        List<Certification> certs = certificationRepository.findByEmployeeEmpId(empId);
        List<Assessment> assessments = assessmentRepository.findByEmployeeEmpId(empId);

        String skillsFormatted = skills.stream()
                .map(s -> s.getSkill().getSkillName() + " " + s.getProficiencyLevel() + "/10")
                .collect(Collectors.joining(", "));

        String certsFormatted = certs.stream()
                .map(c -> c.getCertName() + " " + (c.getStatus() != null ? c.getStatus().name().toLowerCase() : "valid"))
                .collect(Collectors.joining(", "));

        String assessmentFormatted = assessments.isEmpty() ? "No Assessment" :
                "Assessment: " + Math.round(assessments.get(0).getScore()) + "%";

        String formattedSummary = String.format("Skill Service: %s, %s. Skills: %s. %s. %s.",
                employee.getName(),
                employee.getTitle(),
                skillsFormatted.isEmpty() ? "None" : skillsFormatted,
                certsFormatted.isEmpty() ? "No certifications" : certsFormatted,
                assessmentFormatted
        );

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("employee", employee);
        responseMap.put("skills", skills);
        responseMap.put("certifications", certs);
        responseMap.put("assessments", assessments);
        responseMap.put("formattedOutputSummary", formattedSummary);

        return responseMap;
    }

    public Employee getJohnSmith() {
        return employeeRepository.findByName("John Smith")
                .orElseGet(() -> employeeRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("No employees initialized")));
    }
}
