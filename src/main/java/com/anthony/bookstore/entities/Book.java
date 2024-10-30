package com.anthony.bookstore.entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book extends BaseEntity{
    
    @Column(length = 20, unique = true, nullable = false)
    private String isbn;

    @Column(length = 200, nullable = false)
    private String title;

    private Integer year;

    private Float price;

    @Column(length = 100)
    private String genre;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Transient
    private List<Long> authorIds;

    public Book(){

    }

    public Book(Book book){
        this.setId(book.getId());
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.year = book.getYear();
        this.price = book.getPrice();
        this.genre = book.getGenre();
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

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }

    @Override
    public String toString() {
        return "Book [id=" + this.getId() + ", isbn=" + isbn + ", title=" + title + ", year=" + year + ", price=" + price + ", genre=" + genre
                + ", updatedBy=" + updatedBy + ", authorIds=" + authorIds + "]";
    }
}
