package com.skillsphere.controller;

import com.skillsphere.dto.JobDTO;
import com.skillsphere.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/career/jobs", "/api/jobs"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class JobController {

    private final JobService service;

    @PostMapping
    public ResponseEntity<JobDTO> create(@RequestBody JobDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<JobDTO>> getActive() {
        return ResponseEntity.ok(service.getActiveJobs());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
