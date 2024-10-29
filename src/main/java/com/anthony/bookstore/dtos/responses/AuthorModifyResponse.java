package com.anthony.bookstore.dtos.responses;

import java.time.LocalDate;

import com.anthony.bookstore.entities.Author;

public class AuthorModifyResponse extends ResponseStatus{
    private Long id;
    private String name;
    private LocalDate birthday;

    public AuthorModifyResponse(){
        this.name = null;
        this.birthday = null;
    }

    public AuthorModifyResponse(Author author){
        this.id = author.getId();
        this.name = author.getName();
        this.birthday = author.getBirthday();
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
}

