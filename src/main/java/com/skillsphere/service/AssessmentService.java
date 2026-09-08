package com.skillsphere.service;

import com.skillsphere.dto.AssessmentDTO;
import com.skillsphere.entity.Assessment;
import com.skillsphere.entity.Employee;
import com.skillsphere.entity.Skill;
import com.skillsphere.repository.AssessmentRepository;
import com.skillsphere.repository.EmployeeRepository;
import com.skillsphere.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;

    private static final float PASS_THRESHOLD = 70.0f;

    public AssessmentDTO createAssessment(AssessmentDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Skill skill = skillRepository.findById(dto.getSkillId())
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        boolean passed = dto.getScore() != null && dto.getScore() >= PASS_THRESHOLD; // scoring rule

        Assessment assessment = Assessment.builder()
                .employee(employee)
                .skill(skill)
                .score(dto.getScore())
                .passed(passed)
                .verified(false) // verification is a separate HR action
                .title(dto.getTitle() != null ? dto.getTitle() : skill.getSkillName() + " Assessment")
                .assessmentDate(dto.getAssessmentDate() != null ? dto.getAssessmentDate() : LocalDate.now())
                .build();

        Assessment saved = assessmentRepository.save(assessment);

        return AssessmentDTO.builder()
                .assessId(saved.getAssessId())
                .empId(employee.getEmpId())
                .skillId(skill.getSkillId())
                .score(saved.getScore())
                .passed(saved.getPassed())
                .verified(saved.getVerified())
                .title(saved.getTitle())
                .assessmentDate(saved.getAssessmentDate())
                .build();
    }

    public AssessmentDTO verifyAssessment(UUID assessId) {
        Assessment assessment = assessmentRepository.findById(assessId)
                .orElseThrow(() -> new RuntimeException("Assessment not found: " + assessId));
        assessment.setVerified(true);
        Assessment saved = assessmentRepository.save(assessment);
        return toDTO(saved);
    }

    public AssessmentDTO getAssessmentById(UUID assessId) {
        Assessment assessment = assessmentRepository.findById(assessId)
                .orElseThrow(() -> new RuntimeException("Assessment not found: " + assessId));
        return toDTO(assessment);
    }

    public List<AssessmentDTO> getAssessmentsByEmployeeId(UUID empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + empId));

        return assessmentRepository.findByEmployeeId(employee.getId()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AssessmentDTO> getAllAssessments() {
        return assessmentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AssessmentDTO toDTO(Assessment assessment) {
        return AssessmentDTO.builder()
                .assessId(assessment.getAssessId())
                .empId(assessment.getEmployee() != null ? assessment.getEmployee().getEmpId() : null)
                .skillId(assessment.getSkill() != null ? assessment.getSkill().getSkillId() : null)
                .title(assessment.getTitle())
                .score(assessment.getScore())
                .passed(assessment.getPassed())
                .verified(assessment.getVerified())
                .assessmentDate(assessment.getAssessmentDate())
                .build();
    }
}
