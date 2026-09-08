package com.skillsphere.repository;

import com.skillsphere.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, UUID> {
    List<Assessment> findByEmployeeId(UUID employeeId);

    @Query("SELECT a FROM Assessment a WHERE a.employee.id = :empId")
    List<Assessment> findByEmployeeEmpId(@Param("empId") UUID empId);
}
