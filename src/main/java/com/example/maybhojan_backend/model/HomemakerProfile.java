package com.example.maybhojan_backend.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "homemaker_profiles")
public class HomemakerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "onboarding_step")
    private Integer onboardingStep;

    @Column(name = "identity_verified")
    private Boolean identityVerified;

    @Column(name = "documents_uploaded")
    private Boolean documentsUploaded;

    @Column(name = "audit_completed")
    private Boolean auditCompleted;

    @Column(name = "bank_added")
    private Boolean bankAdded;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public HomemakerProfile() {
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getOnboardingStep() {
        return onboardingStep;
    }

    public void setOnboardingStep(Integer onboardingStep) {
        this.onboardingStep = onboardingStep;
    }

    public Boolean getIdentityVerified() {
        return identityVerified;
    }

    public void setIdentityVerified(Boolean identityVerified) {
        this.identityVerified = identityVerified;
    }

    public Boolean getDocumentsUploaded() {
        return documentsUploaded;
    }

    public void setDocumentsUploaded(Boolean documentsUploaded) {
        this.documentsUploaded = documentsUploaded;
    }

    public Boolean getAuditCompleted() {
        return auditCompleted;
    }

    public void setAuditCompleted(Boolean auditCompleted) {
        this.auditCompleted = auditCompleted;
    }

    public Boolean getBankAdded() {
        return bankAdded;
    }

    public void setBankAdded(Boolean bankAdded) {
        this.bankAdded = bankAdded;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}