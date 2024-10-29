package com.anthony.bookstore.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.anthony.bookstore.entities.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByIsbnContaining(String query);
    List<Book> findByTitleContaining(String query);
    List<Book> findByGenreContaining(String query);
    List<Book> findByYear(Integer year);
}