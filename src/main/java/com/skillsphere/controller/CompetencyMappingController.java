package com.skillsphere.controller;

import com.skillsphere.dto.CompetencyGapDTO;
import com.skillsphere.service.CompetencyMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/competency", "/api/v1/competency"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CompetencyMappingController {

    private final CompetencyMappingService competencyMappingService;

    @GetMapping("/gaps")
    public ResponseEntity<List<CompetencyGapDTO>> getGaps(@RequestParam UUID empId, @RequestParam String targetRole) {
        return ResponseEntity.ok(competencyMappingService.getGapsForRole(empId, targetRole));
    }
}
