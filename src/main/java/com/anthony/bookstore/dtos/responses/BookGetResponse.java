package com.anthony.bookstore.dtos.responses;

import java.util.ArrayList;
import java.util.List;

public class BookGetResponse extends ResponseStatus{
    private List<BookGet> bookGetList;
    private Pagination pagination = null;

    public BookGetResponse(){
        bookGetList = new ArrayList<BookGet>();
    }

    public List<BookGet> getBookGetList() {
        return bookGetList;
    }

    public void setBookGetList(List<BookGet> bookGetList) {
        this.bookGetList = bookGetList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
