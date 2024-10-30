package com.anthony.bookstore.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anthony.bookstore.dtos.requests.AuthorAddRequest;
import com.anthony.bookstore.dtos.requests.AuthorModifyRequest;
import com.anthony.bookstore.dtos.responses.AuthorAddResponse;
import com.anthony.bookstore.dtos.responses.AuthorDeleteByIdResponse;
import com.anthony.bookstore.dtos.responses.AuthorGet;
import com.anthony.bookstore.dtos.responses.AuthorGetByIdResponse;
import com.anthony.bookstore.dtos.responses.AuthorGetResponse;
import com.anthony.bookstore.dtos.responses.AuthorModifyResponse;
import com.anthony.bookstore.dtos.responses.BookGet;
import com.anthony.bookstore.dtos.responses.Pagination;
import com.anthony.bookstore.entities.Author;
import com.anthony.bookstore.entities.AuthorBook;
import com.anthony.bookstore.services.AuthorBookService;
import com.anthony.bookstore.services.AuthorService;

@RequestMapping("/author")
@RestController
public class AuthorController extends BaseController{

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorBookService authorBookService;


    @PostMapping("/add")
    public ResponseEntity<AuthorAddResponse> add(@RequestBody AuthorAddRequest authorAddDto) {
    
        AuthorAddResponse response = new AuthorAddResponse();
        prepare(response);

        Author author = new Author();
        author.setName(authorAddDto.getName());
        author.setBirthday(authorAddDto.getBirthday());

        response = new AuthorAddResponse(authorService.add(author));

        List<BookGet> books = new ArrayList<>();
        List<AuthorBook> authorBookList = authorBookService.findAllByAuthorId(response.getId());
        for(AuthorBook authorBook:authorBookList){
            books.add(new BookGet(authorBook.getBook()));
        }

        response.setBooks(books);

        conclude(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<AuthorModifyResponse> add(@RequestBody AuthorModifyRequest authorModifyDto) {
        AuthorModifyResponse response = new AuthorModifyResponse();
        prepare(response);

        Author author = new Author();
        author.setId(authorModifyDto.getId());
        author.setName(authorModifyDto.getName());
        author.setBirthday(authorModifyDto.getBirthday());
        author = authorService.modify(author);

        if(author == null){
            response.setErrorMessage("Name and Birthday combination already exists.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else{
            response = new AuthorModifyResponse(author);
            List<BookGet> books = new ArrayList<>();
            List<AuthorBook> authorBookList = authorBookService.findAllByAuthorId(response.getId());
            for(AuthorBook authorBook:authorBookList){
                books.add(new BookGet(authorBook.getBook()));
            }

            response.setBooks(books);
        }

        conclude(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorGetByIdResponse> getById(@PathVariable Long id) {
        AuthorGetByIdResponse response = new AuthorGetByIdResponse();
        prepare(response);

        Optional<Author> authorOpt = authorService.findById(id);
        if(authorOpt.isPresent()){
            response = new AuthorGetByIdResponse(authorOpt.get());
            List<BookGet> books = new ArrayList<>();
            List<AuthorBook> authorBookList = authorBookService.findAllByAuthorId(response.getId());
            for(AuthorBook authorBook:authorBookList){
                books.add(new BookGet(authorBook.getBook()));
            }

            response.setBooks(books);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        conclude(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<AuthorGetResponse> getAll(@RequestParam(required=false) String query, @RequestParam(required=false) Integer offset, @RequestParam(required=false) Integer limit) {
        AuthorGetResponse response = new AuthorGetResponse();
        prepare(response);

        List<Author> authorList = authorService.getAll(query);
        
        if(authorList != null){
            if(offset!=null && limit!=null){
                Pagination pagination = new Pagination();
                List<Author> temp = new ArrayList<Author>();

                pagination.setTotalItems(authorList.size());
                pagination.setTotalPages((authorList.size() + limit -1) / limit);
                pagination.setLimit(limit);
                pagination.setOffset(offset);

                for (int i = limit*offset; i < (limit*(offset+1)) && i < authorList.size(); i++) {
                    temp.add(authorList.get(i));
                }
                pagination.setNumOfItems(temp.size());
                response.setPagination(pagination);
                authorList = temp;
            }

            List<AuthorGet> authorGetList = new ArrayList<>();
            for(Author author:authorList){
                List<BookGet> books = new ArrayList<>();
                List<AuthorBook> authorBookList = authorBookService.findAllByAuthorId(author.getId());
                for(AuthorBook authorBook:authorBookList){
                    books.add(new BookGet(authorBook.getBook()));
                }
                AuthorGet authorGet = new AuthorGet(author,books);
                authorGetList.add(authorGet);
            }
            response.setAuthors(authorGetList);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        conclude(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDeleteByIdResponse> deleteById(@PathVariable Long id) {
        AuthorDeleteByIdResponse response = new AuthorDeleteByIdResponse();
        prepare(response);

        Author author = new Author();
        author.setId(id);
        List<AuthorBook> authorBook = authorBookService.findAllByAuthorId(id);
        if(authorBook!=null && !authorBook.isEmpty()){
            response.setErrorMessage("Author has books, delete books first.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        id = authorService.delete(author);

        if(id == null){
            response.setErrorMessage("Author not found.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else
            response.setId(id);

        conclude(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
