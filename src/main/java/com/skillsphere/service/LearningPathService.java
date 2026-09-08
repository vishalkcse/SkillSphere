package com.skillsphere.service;

import com.skillsphere.dto.LearningPathDTO;
import com.skillsphere.entity.Course;
import com.skillsphere.entity.LearningPath;
import com.skillsphere.entity.LearningPathCourse;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.CourseRepository;
import com.skillsphere.repository.LearningPathCourseRepository;
import com.skillsphere.repository.LearningPathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LearningPathService {

    private final LearningPathRepository pathRepository;
    private final CourseRepository courseRepository;
    private final LearningPathCourseRepository pathCourseRepository;
    private final CourseService courseService;

    public LearningPath createPath(LearningPath path) {
        if (path.getProgress() == null) path.setProgress(0);
        if (path.getActive() == null) path.setActive(true);
        if (path.getName() == null && path.getTargetRole() != null) {
            path.setName(path.getTargetRole() + " Track");
        }
        return pathRepository.save(path);
    }

    public LearningPath addCourseToPath(UUID pathId, UUID courseId, Integer sequence) {
        LearningPath path = pathRepository.findById(pathId)
                .orElseThrow(() -> new ResourceNotFoundException("Learning path not found: " + pathId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        LearningPathCourse pathCourse = LearningPathCourse.builder()
                .learningPath(path)
                .course(course)
                .sequenceOrder(sequence != null ? sequence : 1)
                .build();

        pathCourseRepository.save(pathCourse);
        return path;
    }

    public List<LearningPathCourse> getPathCourses(UUID pathId) {
        return pathCourseRepository.findByLearningPathPathIdOrderBySequenceOrder(pathId);
    }

    public List<LearningPathDTO> getAllPaths() {
        return pathRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public List<LearningPathDTO> getPathsForRole(String targetRole) {
        return pathRepository.findByTargetRole(targetRole).stream()
                .map(this::toDTO)
                .toList();
    }

    public LearningPathDTO toDTO(LearningPath path) {
        List<LearningPathCourse> pathCourses = getPathCourses(path.getPathId());
        List<Course> courses = pathCourses.stream().map(LearningPathCourse::getCourse).toList();

        return LearningPathDTO.builder()
                .pathId(path.getPathId())
                .name(path.getName() != null ? path.getName() : path.getTitle())
                .title(path.getTitle())
                .description(path.getDescription())
                .careerTrack(path.getCareerTrack())
                .targetRole(path.getTargetRole())
                .progress(path.getProgress())
                .active(path.getActive())
                .courses(courses.stream().map(courseService::toDTO).toList())
                .build();
    }
}
