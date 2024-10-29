package com.anthony.bookstore.entities;

import jakarta.persistence.*;
@Entity
@Table(name = "user_permission")
public class UserPermission extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "path_id", nullable = false)
    private Path path;

    @Column(nullable = false)
    private Boolean allowed = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

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

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }
}
