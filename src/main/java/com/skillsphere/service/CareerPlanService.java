package com.skillsphere.service;

import com.skillsphere.dto.CareerPlanDTO;
import com.skillsphere.entity.CareerPlan;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.CareerPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CareerPlanService {

    private final CareerPlanRepository repository;

    public CareerPlanDTO create(CareerPlanDTO dto) {
        int score = calculatePromotionScore(dto);
        CareerPlan plan = CareerPlan.builder()
                .empId(dto.getEmpId())
                .employeeName(dto.getEmployeeName())
                .currentRole(dto.getCurrentRole())
                .targetRole(dto.getTargetRole())
                .progress(dto.getProgress() != null ? dto.getProgress() : 0)
                .mentor(dto.getMentor())
                .skillGaps(dto.getSkillGaps())
                .trainingPlan(dto.getTrainingPlan())
                .promotionScore(score)
                .promotionEligible(score >= 80)
                .status(dto.getStatus() != null ? CareerPlan.PlanStatus.valueOf(dto.getStatus().toUpperCase()) : CareerPlan.PlanStatus.ACTIVE)
                .build();
        return toDTO(repository.save(plan));
    }

    public List<CareerPlanDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public CareerPlanDTO getById(UUID id) {
        CareerPlan plan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Career plan not found: " + id));
        return toDTO(plan);
    }

    public List<CareerPlanDTO> getByEmployee(UUID empId) {
        return repository.findByEmpId(empId).stream().map(this::toDTO).toList();
    }

    public CareerPlanDTO update(UUID id, CareerPlanDTO dto) {
        CareerPlan plan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Career plan not found: " + id));

        plan.setCurrentRole(dto.getCurrentRole());
        plan.setTargetRole(dto.getTargetRole());
        plan.setProgress(dto.getProgress());
        plan.setMentor(dto.getMentor());
        plan.setSkillGaps(dto.getSkillGaps());
        plan.setTrainingPlan(dto.getTrainingPlan());
        if (dto.getStatus() != null) {
            try {
                plan.setStatus(CareerPlan.PlanStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (Exception ignored) {}
        }

        int score = calculatePromotionScore(dto);
        plan.setPromotionScore(score);
        plan.setPromotionEligible(score >= 80);

        return toDTO(repository.save(plan));
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Career plan not found: " + id);
        }
        repository.deleteById(id);
    }

    private int calculatePromotionScore(CareerPlanDTO dto) {
        int score = 0;
        if (dto.getProgress() != null) {
            score += (int) (dto.getProgress() * 0.6);
        }
        if (dto.getSkillGaps() == null || dto.getSkillGaps().isBlank()) {
            score += 20;
        }
        if (dto.getTrainingPlan() != null && !dto.getTrainingPlan().isBlank()) {
            score += 20;
        }
        return Math.min(score, 100);
    }

    public CareerPlanDTO toDTO(CareerPlan p) {
        return CareerPlanDTO.builder()
                .planId(p.getPlanId())
                .empId(p.getEmpId())
                .employeeName(p.getEmployeeName())
                .currentRole(p.getCurrentRole())
                .targetRole(p.getTargetRole())
                .progress(p.getProgress())
                .mentor(p.getMentor())
                .skillGaps(p.getSkillGaps())
                .trainingPlan(p.getTrainingPlan())
                .promotionScore(p.getPromotionScore())
                .promotionEligible(p.getPromotionEligible())
                .status(p.getStatus() != null ? p.getStatus().name() : "ACTIVE")
                .build();
    }
}
