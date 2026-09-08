package com.skillsphere.service;

import com.skillsphere.dto.AnalyticsDTO;
import com.skillsphere.entity.CareerPlan;
import com.skillsphere.repository.CareerPlanRepository;
import com.skillsphere.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final CareerPlanRepository careerPlanRepository;
    private final JobRepository jobRepository;

    public AnalyticsDTO getAnalytics() {
        List<CareerPlan> plans = careerPlanRepository.findAll();

        double averageProgress = plans.stream()
                .filter(p -> p.getProgress() != null)
                .mapToInt(CareerPlan::getProgress)
                .average()
                .orElse(0.0);

        double roundedProgress = Math.round(averageProgress * 100.0) / 100.0;
        double roundedCoverage = Math.round(calculateSkillCoverage(plans) * 100.0) / 100.0;

        return AnalyticsDTO.builder()
                .totalCareerPlans(plans.size())
                .activeCareerPlans(careerPlanRepository.countByStatus(CareerPlan.PlanStatus.ACTIVE))
                .completedPlans(careerPlanRepository.countByStatus(CareerPlan.PlanStatus.COMPLETED))
                .promotionEligible(careerPlanRepository.countByPromotionEligibleTrue())
                .averageProgress(roundedProgress)
                .skillCoverage(roundedCoverage)
                .activeJobs(jobRepository.findByActiveTrue().size())
                .build();
    }

    private double calculateSkillCoverage(List<CareerPlan> plans) {
        if (plans.isEmpty()) return 0.0;
        long withoutGap = plans.stream()
                .filter(p -> p.getSkillGaps() == null || p.getSkillGaps().isBlank())
                .count();
        return (withoutGap * 100.0) / plans.size();
    }
}
