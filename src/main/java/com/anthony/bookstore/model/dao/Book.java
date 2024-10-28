package com.anthony.bookstore.model.dao;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "book")
public class Book {
    
    @Id
    @GeneratedValue
    @Column(length = 36)
    private UUID id;

    @Column(length = 20, unique = true, nullable = false)
    private String isbn;

    @Column(length = 200, nullable = false)
    private String title;

    private Integer year;
    private Float price;

    @Column(length = 100)
    private String genre;

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
        return "Book [id=" + id + ", isbn=" + isbn + ", title=" + title + ", year=" + year + ", price=" + price
                + ", genre=" + genre + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", updatedBy="
                + updatedBy + "]";
    }
}
