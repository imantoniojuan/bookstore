package com.anthony.bookstore.dtos.responses;

import java.time.LocalDate;
import java.util.List;

import com.anthony.bookstore.entities.Author;

public class AuthorGet{
    private Long id;
    private String name;
    private LocalDate birthday;
    private List<BookGet> books;

    public AuthorGet() {
    }

    public AuthorGet(Author author){
        this.id = author.getId();
        this.name = author.getName();
        this.birthday = author.getBirthday();
    }

    public AuthorGet(Author author, List<BookGet> books){
        this.id = author.getId();
        this.name = author.getName();
        this.birthday = author.getBirthday();
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<BookGet> getBooks() {
        return books;
    }

    public void setBooks(List<BookGet> books) {
        this.books = books;
    }
    
}
