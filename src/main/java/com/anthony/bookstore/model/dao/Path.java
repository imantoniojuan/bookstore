package com.anthony.bookstore.model.dao;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "path")
public class Path {
    
    @Id
    @GeneratedValue
    @Column(length = 36)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String path;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "Path [id=" + id + ", path=" + path + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
                + ", updatedBy=" + updatedBy + "]";
    }
}
