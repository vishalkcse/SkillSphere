package com.skillsphere.repository;

import com.skillsphere.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, UUID> {
    List<Certification> findByEmployeeId(UUID empId);

    @Query("SELECT c FROM Certification c WHERE c.employee.id = :empId")
    List<Certification> findByEmployeeEmpId(@Param("empId") UUID empId);

    List<Certification> findByStatus(Certification.Status status);
    List<Certification> findByExpiryBetween(LocalDate start, LocalDate end);
}
