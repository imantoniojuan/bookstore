package com.anthony.bookstore.dtos.responses;

public class BookDeleteByIdResponse extends ResponseStatus{
    private Long id;

    public BookDeleteByIdResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

