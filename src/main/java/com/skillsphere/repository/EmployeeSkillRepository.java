package com.skillsphere.repository;

import com.skillsphere.entity.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, UUID> {
    List<EmployeeSkill> findByEmployeeId(UUID employeeId);

    @Query("SELECT es FROM EmployeeSkill es WHERE es.employee.id = :empId")
    List<EmployeeSkill> findByEmployeeEmpId(@Param("empId") UUID empId);
}
