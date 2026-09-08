package com.skillsphere.controller;

import com.skillsphere.dto.AnalyticsDTO;
import com.skillsphere.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/career/analytics", "/api/analytics"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService service;

    @GetMapping
    public ResponseEntity<AnalyticsDTO> getAnalytics() {
        return ResponseEntity.ok(service.getAnalytics());
    }
}
