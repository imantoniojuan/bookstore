package com.anthony.bookstore.dtos.requests;

import java.util.List;

public class BookModifyRequest {
    private Long id;
    private String isbn;
    private String title;
    private Integer year;
    private Float price;
    private String genre;
    private List<Long> authorIds;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public List<Long> getAuthorIds() {
        return authorIds;
    }
    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }
    
}
