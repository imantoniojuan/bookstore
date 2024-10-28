package com.anthony.bookstore.model.dao;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {
    
    @Id
    @GeneratedValue
    @Column(length = 36)
    private UUID id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String pHash;

    private int totp;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getpHash() {
        return pHash;
    }

    public void setpHash(String pHash) {
        this.pHash = pHash;
    }

    public int getTotp() {
        return totp;
    }

    public void setTotp(int totp) {
        this.totp = totp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", pHash=" + pHash + ", totp=" + totp + ", createdAt="
                + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}

