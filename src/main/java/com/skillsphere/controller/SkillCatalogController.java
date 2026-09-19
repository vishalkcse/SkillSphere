package com.skillsphere.controller;

import com.skillsphere.dto.SkillDTO;
import com.skillsphere.service.SkillCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/skills/catalog")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SkillCatalogController {

    private final SkillCatalogService skillCatalogService;

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAllSkills() {
        return ResponseEntity.ok(skillCatalogService.getAllSkills());
    }

    @PostMapping
    // @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<SkillDTO> addSkill(@RequestBody SkillDTO skillDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(skillCatalogService.addSkill(skillDTO));
    }
}
