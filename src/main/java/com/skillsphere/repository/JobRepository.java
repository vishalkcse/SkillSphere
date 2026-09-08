package com.skillsphere.repository;

import com.skillsphere.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
    List<Job> findByActiveTrue();
    List<Job> findByDepartmentIgnoreCase(String department);
}
