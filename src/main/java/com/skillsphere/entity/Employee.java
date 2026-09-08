package com.skillsphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "employees")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "emp_id")
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String title;

    private String department;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private Role role;

    public Employee(String name, String email, String password, String title, String department, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.title = title;
        this.department = department;
        this.role = role;
    }

    public UUID getEmpId() {
        return id;
    }

    public void setEmpId(UUID empId) {
        this.id = empId;
    }
}
