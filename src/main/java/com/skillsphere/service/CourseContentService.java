package com.skillsphere.service;

import com.skillsphere.entity.Course;
import com.skillsphere.entity.CourseContent;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.CourseContentRepository;
import com.skillsphere.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseContentService {

    private final CourseContentRepository contentRepository;
    private final CourseRepository courseRepository;

    public CourseContent addContent(UUID courseId, CourseContent content) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
        content.setCourse(course);
        return contentRepository.save(content);
    }

    public List<CourseContent> getCourseContent(UUID courseId) {
        return contentRepository.findByCourseCourseIdOrderBySequenceOrder(courseId);
    }
}
