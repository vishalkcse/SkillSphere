package com.skillsphere.controller;

import com.skillsphere.entity.CourseContent;
import com.skillsphere.service.CourseContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/learning/content")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CourseContentController {

    private final CourseContentService contentService;

    @PostMapping("/course/{courseId}")
    public ResponseEntity<CourseContent> addContent(
            @PathVariable UUID courseId,
            @RequestBody CourseContent content) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.addContent(courseId, content));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CourseContent>> getContent(@PathVariable UUID courseId) {
        return ResponseEntity.ok(contentService.getCourseContent(courseId));
    }
}
