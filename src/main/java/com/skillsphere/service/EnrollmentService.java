package com.skillsphere.service;

import com.skillsphere.dto.EnrollmentDTO;
import com.skillsphere.entity.Course;
import com.skillsphere.entity.Enrollment;
import com.skillsphere.repository.CourseRepository;
import com.skillsphere.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentDTO enroll(UUID empId, UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = Enrollment.builder()
                .empId(empId)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .progress(0)
                .completed(false)
                .score(0.0f)
                .build();

        return toDTO(enrollmentRepository.save(enrollment));
    }

    public List<EnrollmentDTO> getEmployeeEnrollments(UUID empId) {
        return enrollmentRepository.findByEmpId(empId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public EnrollmentDTO updateProgress(UUID enrollmentId, Integer progress, Float score) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found: " + enrollmentId));

        enrollment.setProgress(progress);
        if (score != null) {
            enrollment.setScore(score);
        }
        if (progress != null && progress >= 100) {
            enrollment.setCompleted(true);
            enrollment.setCompletedAt(LocalDateTime.now());
        }
        return toDTO(enrollmentRepository.save(enrollment));
    }

    public EnrollmentDTO toDTO(Enrollment e) {
        return EnrollmentDTO.builder()
                .enrollmentId(e.getEnrollmentId())
                .empId(e.getEmpId())
                .courseId(e.getCourse() != null ? e.getCourse().getCourseId() : null)
                .courseTitle(e.getCourse() != null ? e.getCourse().getTitle() : null)
                .enrolledAt(e.getEnrolledAt())
                .progress(e.getProgress())
                .completed(e.getCompleted())
                .score(e.getScore())
                .completedAt(e.getCompletedAt())
                .build();
    }
}
