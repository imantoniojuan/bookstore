package com.anthony.bookstore.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.anthony.bookstore.entities.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findByNameAndBirthday(String name, LocalDate birthday);
    List<Author> findByNameContaining(String query);
}