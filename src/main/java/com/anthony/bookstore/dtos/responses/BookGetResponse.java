package com.anthony.bookstore.dtos.responses;

import java.util.ArrayList;
import java.util.List;

public class BookGetResponse extends ResponseStatus{
    private List<BookGet> books;
    private Pagination pagination = null;

    public BookGetResponse(){
        books = new ArrayList<BookGet>();
    }

    public List<BookGet> getBooks() {
        return books;
    }

    public void setBooks(List<BookGet> books) {
        this.books = books;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
