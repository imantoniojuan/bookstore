package com.anthony.bookstore.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "author")
public class Author extends BaseEntity {
    @Column(length = 100, nullable = false)
    private String name;

    private LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "Author [id=" + this.getId() + " name=" + name + ", birthday=" + birthday + ", updatedBy=" + updatedBy + "]";
    }
}
