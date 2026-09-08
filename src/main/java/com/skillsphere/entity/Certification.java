package com.skillsphere.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cert_id")
    private UUID certId;

    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    @Column(name = "name", nullable = false)
    private String name;

    private String issuingOrganization;

    private String credentialId;

    @Column(name = "issued")
    private LocalDate issued;

    @Column(name = "expiry")
    private LocalDate expiry;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private Status status;

    public enum Status {
        VALID, EXPIRED, PENDING_RENEWAL
    }

    public Certification(Employee employee, String certName, String credentialId, LocalDate issueDate, LocalDate expiryDate, String statusStr) {
        this.employee = employee;
        this.name = certName;
        this.credentialId = credentialId;
        this.issued = issueDate;
        this.expiry = expiryDate;
        if (statusStr != null) {
            try {
                this.status = Status.valueOf(statusStr.toUpperCase());
            } catch (Exception e) {
                this.status = Status.VALID;
            }
        } else {
            this.status = Status.VALID;
        }
    }

    public UUID getId() {
        return certId;
    }

    public void setId(UUID id) {
        this.certId = id;
    }

    public String getCertName() {
        return name;
    }

    public void setCertName(String certName) {
        this.name = certName;
    }

    public LocalDate getIssueDate() {
        return issued;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issued = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiry;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiry = expiryDate;
    }
}
