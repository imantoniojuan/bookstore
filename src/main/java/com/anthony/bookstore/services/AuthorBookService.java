package com.anthony.bookstore.services;

import com.anthony.bookstore.entities.AuthorBook;
import com.anthony.bookstore.repositories.AuthorBookRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorBookService {
    private final AuthorBookRepository authorBookRepository;

    public AuthorBookService(AuthorBookRepository authorBookRepository) {
        this.authorBookRepository = authorBookRepository;
    }

    public List<AuthorBook> findAllByBookId(Long id){
        return authorBookRepository.findAllByBookId(id);
    }

    public List<AuthorBook> findAllByAuthorId(Long id){
        return authorBookRepository.findAllByAuthorId(id);
    }


}