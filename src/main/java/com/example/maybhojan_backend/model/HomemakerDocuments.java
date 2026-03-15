package com.example.maybhojan_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "homemaker_documents")
public class HomemakerDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String govtIdUrl;

    private String fssaiUrl;

    private String kitchenPhotoUrl;

    private String verificationStatus = "PENDING";

    private LocalDateTime submittedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public HomemakerDocuments() {
    }

    public Long getId() {
        return id;
    }

    public String getGovtIdUrl() {
        return govtIdUrl;
    }

    public void setGovtIdUrl(String govtIdUrl) {
        this.govtIdUrl = govtIdUrl;
    }

    public String getFssaiUrl() {
        return fssaiUrl;
    }

    public void setFssaiUrl(String fssaiUrl) {
        this.fssaiUrl = fssaiUrl;
    }

    public String getKitchenPhotoUrl() {
        return kitchenPhotoUrl;
    }

    public void setKitchenPhotoUrl(String kitchenPhotoUrl) {
        this.kitchenPhotoUrl = kitchenPhotoUrl;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}