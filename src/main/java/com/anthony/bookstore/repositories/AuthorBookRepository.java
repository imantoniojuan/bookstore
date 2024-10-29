package com.anthony.bookstore.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.anthony.bookstore.entities.AuthorBook;

@Repository
public interface AuthorBookRepository extends CrudRepository<AuthorBook, Long> {
    void deleteAllByBookId(Long id);
    List<AuthorBook> findAllByBookId(Long id);
    List<AuthorBook> findAllByAuthorId(Long id);
}