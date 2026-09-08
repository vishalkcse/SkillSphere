package com.skillsphere.service;

import com.skillsphere.dto.CourseDTO;
import com.skillsphere.entity.Course;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public CourseDTO getCourse(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
        return toDTO(course);
    }

    public CourseDTO getCourseById(UUID courseId) {
        return getCourse(courseId);
    }

    public CourseDTO createCourse(CourseDTO dto) {
        Course.CourseType type = Course.CourseType.ONLINE;
        if (dto.getType() != null) {
            try {
                type = Course.CourseType.valueOf(dto.getType().toUpperCase());
            } catch (Exception ignored) {
            }
        }

        Integer durationVal = dto.getDuration() != null ? dto.getDuration() : dto.getDurationHours();

        Course course = Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .duration(durationVal)
                .type(type)
                .instructor(dto.getInstructor() != null ? dto.getInstructor() : "Jane Doe")
                .rating(dto.getRating() != null ? dto.getRating() : 4.85)
                .active(dto.getActive() != null ? dto.getActive() : true)
                .category(dto.getCategory())
                .durationHours(durationVal)
                .completionRate(dto.getCompletionRate() != null ? dto.getCompletionRate() : 87.0f)
                .build();

        return toDTO(courseRepository.save(course));
    }

    public void deleteCourse(UUID courseId) {
        courseRepository.deleteById(courseId);
    }

    public CourseDTO toDTO(Course course) {
        Integer durationVal = course.getDuration() != null ? course.getDuration() : course.getDurationHours();
        return CourseDTO.builder()
                .courseId(course.getCourseId())
                .title(course.getTitle())
                .description(course.getDescription())
                .duration(durationVal)
                .type(course.getType() != null ? course.getType().name() : "ONLINE")
                .instructor(course.getInstructor())
                .rating(course.getRating())
                .active(course.getActive())
                .category(course.getCategory())
                .durationHours(durationVal)
                .completionRate(course.getCompletionRate())
                .build();
    }
}
