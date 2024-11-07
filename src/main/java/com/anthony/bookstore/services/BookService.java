package com.anthony.bookstore.services;

import com.anthony.bookstore.entities.Author;
import com.anthony.bookstore.entities.AuthorBook;
import com.anthony.bookstore.entities.Book;
import com.anthony.bookstore.repositories.AuthorBookRepository;
import com.anthony.bookstore.repositories.BookRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorBookRepository authorBookRepository;

    public BookService(BookRepository bookRepository, AuthorBookRepository authorBookRepository) {
        this.bookRepository = bookRepository;
        this.authorBookRepository = authorBookRepository;
    }

    public Book add(Book book, List<Author> authorList){
        Optional<Book> bookOpt = bookRepository.findByIsbn(book.getIsbn());
        if(bookOpt.isPresent()){
            return bookOpt.get();
        }
        else{
            book = bookRepository.save(book);
            for(Author author:authorList){
                AuthorBook authorBook = new AuthorBook();
                authorBook.setBook(book);
                authorBook.setAuthor(author);
                authorBookRepository.save(authorBook);
            }
            return book;
        }
        
    }

    public Book modify(Book book, List<Author> authorList){
        Optional<Book> bookOpt = bookRepository.findById(book.getId());
        if(bookOpt.isPresent()){
            Optional<Book> bookOpt2 = bookRepository.findByIsbn(book.getIsbn());
            if(bookOpt2.isPresent() && (bookOpt.get().getId()!=bookOpt.get().getId())){
                return null;
            }
            Book temp = bookOpt.get();
            temp.setIsbn(book.getIsbn());
            temp.setTitle(book.getTitle());
            temp.setPrice(book.getPrice());
            temp.setYear(book.getYear());
            temp.setGenre(book.getGenre());
            book = bookRepository.save(temp);
            List<AuthorBook> authorBookList = authorBookRepository.findAllByBookId(book.getId());
            authorBookRepository.deleteAll(authorBookList);
            for(Author author:authorList){
                AuthorBook authorBook = new AuthorBook();
                authorBook.setBook(book);
                authorBook.setAuthor(author);
                authorBookRepository.save(authorBook);
            }
            return book;
        }
        else{
           return null;
        }
    }

    public Long delete(Book book){
        Optional<Book> bookOpt = bookRepository.findById(book.getId());
        if(bookOpt.isPresent()){
            List<AuthorBook> authorBookList = authorBookRepository.findAllByBookId(bookOpt.get().getId());
            authorBookRepository.deleteAll(authorBookList);
            bookRepository.delete(bookOpt.get());
            return book.getId();
        }
        else{
            return null;
        }
    }

    public Optional<Book> findById(Long id){
        return bookRepository.findById(id);
    }


    public List<Book> findByTitleContaining(String query){
        return bookRepository.findByTitleContaining(query);
    }

    public List<Book> allBooks() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    public List<Book> getAll(String query) {
        List<Book> books = new ArrayList<>();
        if(query!=null){
            books.addAll(bookRepository.findByTitleContaining(query));
            books.addAll(bookRepository.findByGenreContaining(query));
            books.addAll(bookRepository.findByIsbnContaining(query));
            try{
                Integer year = Integer.getInteger(query);
                books.addAll(bookRepository.findByYear(year));
            }
            catch(Exception e){}
        }
        else{
            bookRepository.findAll().forEach(books::add);
        }
        return books;
    }

    public List<Book> getByTitle(String query) {
        List<Book> books = new ArrayList<>();
        if(query!=null){
            books.addAll(bookRepository.findByTitleContaining(query));
        }
        else{
            bookRepository.findAll().forEach(books::add);
        }
        return books;
    }
}