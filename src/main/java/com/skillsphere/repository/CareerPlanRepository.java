package com.skillsphere.repository;

import com.skillsphere.entity.CareerPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CareerPlanRepository extends JpaRepository<CareerPlan, UUID> {
    List<CareerPlan> findByEmpId(UUID empId);
    long countByStatus(CareerPlan.PlanStatus status);
    long countByPromotionEligibleTrue();
}
