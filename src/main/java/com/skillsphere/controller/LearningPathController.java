package com.skillsphere.controller;

import com.skillsphere.dto.LearningPathDTO;
import com.skillsphere.entity.LearningPath;
import com.skillsphere.entity.LearningPathCourse;
import com.skillsphere.service.LearningPathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/learning/paths")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LearningPathController {

    private final LearningPathService learningPathService;

    @GetMapping
    public ResponseEntity<List<LearningPathDTO>> getAllPaths() {
        return ResponseEntity.ok(learningPathService.getAllPaths());
    }

    @GetMapping("/role/{roleTitle}")
    public ResponseEntity<List<LearningPathDTO>> getPathsForRole(@PathVariable String roleTitle) {
        return ResponseEntity.ok(learningPathService.getPathsForRole(roleTitle));
    }

    @PostMapping
    public ResponseEntity<LearningPath> createPath(@RequestBody LearningPath path) {
        return ResponseEntity.status(HttpStatus.CREATED).body(learningPathService.createPath(path));
    }

    @PostMapping("/{pathId}/courses/{courseId}")
    public ResponseEntity<LearningPath> addCourse(
            @PathVariable UUID pathId,
            @PathVariable UUID courseId,
            @RequestParam(required = false, defaultValue = "1") Integer sequence) {
        return ResponseEntity.ok(learningPathService.addCourseToPath(pathId, courseId, sequence));
    }

    @GetMapping("/{pathId}/courses")
    public ResponseEntity<List<LearningPathCourse>> getCourses(@PathVariable UUID pathId) {
        return ResponseEntity.ok(learningPathService.getPathCourses(pathId));
    }
}
