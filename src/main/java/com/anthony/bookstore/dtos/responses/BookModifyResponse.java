package com.anthony.bookstore.dtos.responses;

import java.util.List;

import com.anthony.bookstore.entities.Book;

public class BookModifyResponse extends ResponseStatus{
    private Long id;
    private String isbn;
    private String title;
    private Integer year;
    private Float price;
    private String genre;
    private List<AuthorGet> authors;

    public BookModifyResponse() {
    }

    public BookModifyResponse(Book book){
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.year = book.getYear();
        this.price = book.getPrice();
        this.genre = book.getGenre();
    }

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
    public List<AuthorGet> getAuthors() {
        return authors;
    }
    public void setAuthors(List<AuthorGet> authors) {
        this.authors = authors;
    }
    
}

