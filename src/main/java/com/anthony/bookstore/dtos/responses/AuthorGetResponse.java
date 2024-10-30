package com.anthony.bookstore.dtos.responses;

import java.util.ArrayList;
import java.util.List;

import com.anthony.bookstore.entities.Author;

public class AuthorGetResponse extends ResponseStatus{
    private List<AuthorGet> authors;
    private Pagination pagination = null;

    public AuthorGetResponse(){
        authors = new ArrayList<AuthorGet>();
    }

    public List<AuthorGet> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorGet> authors) {
        this.authors = authors;
    }

    public void setAuthorsWithAuthorList(List<Author> authorList){
        authors = new ArrayList<AuthorGet>();
        for(Author author:authorList){
            authors.add(new AuthorGet(author));
        }
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
