package com.skillsphere.service;

import com.skillsphere.entity.Enrollment;
import com.skillsphere.entity.LearningCertificate;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.EnrollmentRepository;
import com.skillsphere.repository.LearningCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final EnrollmentRepository enrollmentRepository;
    private final LearningCertificateRepository certificateRepository;

    public LearningCertificate generateCertificate(UUID enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found: " + enrollmentId));

        if (!Boolean.TRUE.equals(enrollment.getCompleted())) {
            throw new RuntimeException("Course is not completed");
        }

        LearningCertificate certificate = LearningCertificate.builder()
                .empId(enrollment.getEmpId())
                .courseId(enrollment.getCourse() != null ? enrollment.getCourse().getCourseId() : null)
                .courseName(enrollment.getCourse() != null ? enrollment.getCourse().getTitle() : "SkillSphere Course")
                .score(enrollment.getScore())
                .issuedDate(LocalDate.now())
                .certificateNumber("SS-" + UUID.randomUUID())
                .build();

        return certificateRepository.save(certificate);
    }

    public List<LearningCertificate> getCertificatesByEmployeeId(UUID empId) {
        return certificateRepository.findByEmpId(empId);
    }
}
