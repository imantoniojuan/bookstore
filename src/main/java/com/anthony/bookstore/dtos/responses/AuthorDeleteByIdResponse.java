package com.anthony.bookstore.dtos.responses;

public class AuthorDeleteByIdResponse extends ResponseStatus{
    private Long id;

    public AuthorDeleteByIdResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

