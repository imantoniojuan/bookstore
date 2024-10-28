package com.anthony.bookstore.model.dao;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_permission")
public class UserPermission {

    @Id
    @GeneratedValue
    @Column(length = 36)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "path_id", nullable = false)
    private Path path;

    @Column(nullable = false)
    private Boolean allowed = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return "UserPermission [id=" + id + ", path=" + path + ", allowed=" + allowed + ", user=" + user
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
    }
}
