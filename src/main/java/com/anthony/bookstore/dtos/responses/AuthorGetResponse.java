package com.anthony.bookstore.dtos.responses;

import java.util.ArrayList;
import java.util.List;

import com.anthony.bookstore.entities.Author;

public class AuthorGetResponse extends ResponseStatus{
    private List<AuthorGet> authorGetList;
    private Pagination pagination = null;

    public AuthorGetResponse(){
        authorGetList = new ArrayList<AuthorGet>();
    }

    public List<AuthorGet> getAuthorGetList() {
        return authorGetList;
    }

    public void setAuthorGetList(List<AuthorGet> authorGetList) {
        this.authorGetList = authorGetList;
    }

    public void setAuthorGetListWithAuthorList(List<Author> authorList){
        authorGetList = new ArrayList<AuthorGet>();
        for(Author author:authorList){
            authorGetList.add(new AuthorGet(author));
        }
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
