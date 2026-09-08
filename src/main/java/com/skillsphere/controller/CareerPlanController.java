package com.skillsphere.controller;

import com.skillsphere.dto.CareerPlanDTO;
import com.skillsphere.service.CareerPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/career/plans", "/api/plans"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CareerPlanController {

    private final CareerPlanService service;

    @PostMapping
    public ResponseEntity<CareerPlanDTO> create(@RequestBody CareerPlanDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CareerPlanDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareerPlanDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<CareerPlanDTO>> getByEmployee(@PathVariable UUID empId) {
        return ResponseEntity.ok(service.getByEmployee(empId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CareerPlanDTO> update(@PathVariable UUID id, @RequestBody CareerPlanDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
