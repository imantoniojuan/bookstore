package com.anthony.bookstore.services;

import com.anthony.bookstore.entities.Author;
import com.anthony.bookstore.repositories.AuthorRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author add(Author author){
        Optional<Author> authorOpt = authorRepository.findByNameAndBirthday(author.getName(),author.getBirthday());
        if(authorOpt.isPresent()){
            return authorOpt.get();
        }
        else{
            return authorRepository.save(author);
        }
        
    }

    public Author modify(Author author){

        Optional<Author> authorOpt = authorRepository.findById(author.getId());
        if(authorOpt.isPresent()){
            Optional<Author> authorOpt2 = authorRepository.findByNameAndBirthday(author.getName(),author.getBirthday());
            if(authorOpt2.isPresent() && (authorOpt2.get().getId()!=authorOpt.get().getId())){
                return null;
            }
            Author temp = authorOpt.get();
            temp.setName(author.getName());
            temp.setBirthday(author.getBirthday());
            return authorRepository.save(author);
        }
        else{
            return null;
        }
    }

    public Long delete(Author author){
        Optional<Author> authorOpt = authorRepository.findById(author.getId());
        if(authorOpt.isPresent()){
            authorRepository.delete(authorOpt.get());
            return author.getId();
        }
        else{
            return null;
        }
    }

    public Optional<Author> findById(Long id){
        return authorRepository.findById(id);
    }

    public List<Author> findByIdIn(List<Long> ids){
        return authorRepository.findByIdIn(ids);
    }

    public Optional<Author> findByNameAndBirthday(String name, LocalDate birthday){
        return authorRepository.findByNameAndBirthday(name,birthday);
    }

    public List<Author> findByNameContaining(String query){
        return authorRepository.findByNameContaining(query);
    }

    public List<Author> allAuthors() {
        List<Author> authors = new ArrayList<>();
        authorRepository.findAll().forEach(authors::add);
        return authors;
    }

    public List<Author> getAll(String query) {
        List<Author> authors = new ArrayList<>();
        if(query!=null){
            authors = authorRepository.findByNameContaining(query);
        }
        else{
            authorRepository.findAll().forEach(authors::add);
        }
        return authors;
    }
}