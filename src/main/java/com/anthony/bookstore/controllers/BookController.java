package com.anthony.bookstore.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.anthony.bookstore.dtos.requests.BookAddRequest;
import com.anthony.bookstore.dtos.requests.BookModifyRequest;
import com.anthony.bookstore.dtos.responses.AuthorGet;
import com.anthony.bookstore.dtos.responses.BookAddResponse;
import com.anthony.bookstore.dtos.responses.BookDeleteByIdResponse;
import com.anthony.bookstore.dtos.responses.BookGet;
import com.anthony.bookstore.dtos.responses.BookGetByIdResponse;
import com.anthony.bookstore.dtos.responses.BookGetResponse;
import com.anthony.bookstore.dtos.responses.BookModifyResponse;
import com.anthony.bookstore.dtos.responses.Pagination;
import com.anthony.bookstore.entities.Author;
import com.anthony.bookstore.entities.AuthorBook;
import com.anthony.bookstore.entities.Book;
import com.anthony.bookstore.services.AuthorBookService;
import com.anthony.bookstore.services.AuthorService;
import com.anthony.bookstore.services.BookService;

@RequestMapping("/book")
@RestController
public class BookController extends BaseController{

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorBookService authorBookService;

    @PostMapping("/add")
    public ResponseEntity<BookAddResponse> add(@RequestBody BookAddRequest bookAddDto) {
    
        BookAddResponse response = new BookAddResponse();
        prepare(response);

        List<Author> authorList = authorService.findByIdIn(bookAddDto.getAuthorIds());
        if(authorList!=null && !authorList.isEmpty()){
            Book book = new Book();
            book.setIsbn(bookAddDto.getIsbn());
            book.setTitle(bookAddDto.getTitle());
            book.setPrice(bookAddDto.getPrice());
            book.setYear(bookAddDto.getYear());
            book.setGenre(bookAddDto.getGenre());
            response = new BookAddResponse(bookService.add(book, authorList));

            List<AuthorGet> authors = new ArrayList<>();
            List<AuthorBook> authorBookList = authorBookService.findAllByBookId(book.getId());
            for(AuthorBook authorBook:authorBookList){
                authors.add(new AuthorGet(authorBook.getAuthor()));
            } 
            response.setAuthors(authors);
        }
        else{
            response.setErrorMessage("Invalid Author IDs.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        conclude(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<BookModifyResponse> add(@RequestBody BookModifyRequest bookModifyDto) {
        BookModifyResponse response = new BookModifyResponse();
        prepare(response);


        List<Author> authorList = authorService.findByIdIn(bookModifyDto.getAuthorIds());
        if(authorList!=null && !authorList.isEmpty()){
            Book book = new Book();
            book.setId(bookModifyDto.getId());
            book.setIsbn(bookModifyDto.getIsbn());
            book.setTitle(bookModifyDto.getTitle());
            book.setPrice(bookModifyDto.getPrice());
            book.setYear(bookModifyDto.getYear());
            book.setGenre(bookModifyDto.getGenre());
            book = bookService.modify(book, authorList);
            if(book == null){
                response.setErrorMessage("ISBN already exists for another book.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            else{
                List<AuthorGet> authors = new ArrayList<>();
                List<AuthorBook> authorBookList = authorBookService.findAllByBookId(book.getId());
                for(AuthorBook authorBook:authorBookList){
                    authors.add(new AuthorGet(authorBook.getAuthor()));
                } 
                response = new BookModifyResponse(book);
                response.setAuthors(authors);
            }
                

        }
        else{
            response.setErrorMessage("Invalid Author IDs.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        conclude(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookGetByIdResponse> getById(@PathVariable Long id) {
        BookGetByIdResponse response = new BookGetByIdResponse();
        prepare(response);

        List<AuthorBook> authorBookList = authorBookService.findAllByBookId(id);
        if(authorBookList!=null && !authorBookList.isEmpty()){
            response.setId(authorBookList.get(0).getBook().getId());
            response.setTitle(authorBookList.get(0).getBook().getTitle());
            response.setIsbn(authorBookList.get(0).getBook().getIsbn());
            response.setPrice(authorBookList.get(0).getBook().getPrice());
            response.setYear(authorBookList.get(0).getBook().getYear());
            response.setGenre(authorBookList.get(0).getBook().getGenre());
            
            List<AuthorGet> authors = new ArrayList<>();
            for(AuthorBook authorBook:authorBookList){
                authors.add(new AuthorGet(authorBook.getAuthor()));
            }
            response.setAuthors(authors);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        conclude(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<BookGetResponse> getAll(@RequestParam(required=false) String query, @RequestParam(required=false) Integer offset, @RequestParam(required=false) Integer limit) {
        BookGetResponse response = new BookGetResponse();
        prepare(response);

        List<Book> bookList = bookService.getAll(query);
        if(bookList != null){
            if(offset!=null && limit!=null){
                Pagination pagination = new Pagination();
                List<Book> temp = new ArrayList<Book>();

                pagination.setTotalItems(bookList.size());
                pagination.setTotalPages((bookList.size() + limit -1) / limit);
                pagination.setLimit(limit);
                pagination.setOffset(offset);

                for (int i = limit*offset; i < (limit*(offset+1)) && i < bookList.size(); i++) {
                    temp.add(bookList.get(i));
                }
                pagination.setNumOfItems(temp.size());
                response.setPagination(pagination);
                bookList = temp;
            }
            List<BookGet> bookGetList = new ArrayList<>();
            for(Book book:bookList){
                List<AuthorGet> authors = new ArrayList<>();
                List<AuthorBook> authorBookList = authorBookService.findAllByBookId(book.getId());
                for(AuthorBook authorBook:authorBookList){
                    authors.add(new AuthorGet(authorBook.getAuthor()));
                }
                BookGet bookGet = new BookGet(book,authors);
                bookGetList.add(bookGet);
            }
            response.setBooks(bookGetList);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        conclude(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<BookGetResponse> getByTitleAndAuthor(@RequestParam(required=false) String title, @RequestParam(required=false) String author, @RequestParam(required=false) Integer offset, @RequestParam(required=false) Integer limit) {
        BookGetResponse response = new BookGetResponse();
        prepare(response);

        List<Book> bookList = new ArrayList<Book>();

        List<Book> tempBookList = new ArrayList<Book>();
        List<Book> tempBookAuthorList = new ArrayList<Book>();
        
        if(title!=null && !title.isEmpty()){
            tempBookList = bookService.getByTitle(title);
            bookList = tempBookList;
        }
        if(author!=null && !author.isEmpty()){
            List<Author> authorList = authorService.getAll(author);
            List<AuthorBook> authorBooks = new ArrayList<AuthorBook>();
            for(Author a:authorList){
                authorBooks.addAll(authorBookService.findAllByAuthorId(a.getId()));
            }
            for(AuthorBook ab:authorBooks){
                tempBookAuthorList.add(ab.getBook());
            }
            bookList = tempBookAuthorList;
        }

        if((title!=null && !title.isEmpty()) && (author!=null && !author.isEmpty())){
            System.out.println(tempBookList);
            System.out.println(tempBookAuthorList);
            Set<Book> bookSet = tempBookList.stream()
            .distinct()
            .filter(tempBookAuthorList::contains)
            .collect(Collectors.toSet());
            bookList = new ArrayList<>(bookSet);
        }

        if(bookList != null){
            if(offset!=null && limit!=null){
                Pagination pagination = new Pagination();
                List<Book> temp = new ArrayList<Book>();

                pagination.setTotalItems(bookList.size());
                pagination.setTotalPages((bookList.size() + limit -1) / limit);
                pagination.setLimit(limit);
                pagination.setOffset(offset);

                for (int i = limit*offset; i < (limit*(offset+1)) && i < bookList.size(); i++) {
                    temp.add(bookList.get(i));
                }
                pagination.setNumOfItems(temp.size());
                response.setPagination(pagination);
                bookList = temp;
            }
            List<BookGet> bookGetList = new ArrayList<>();
            for(Book book:bookList){
                List<AuthorGet> authors = new ArrayList<>();
                List<AuthorBook> authorBookList = authorBookService.findAllByBookId(book.getId());
                for(AuthorBook authorBook:authorBookList){
                    authors.add(new AuthorGet(authorBook.getAuthor()));
                }
                BookGet bookGet = new BookGet(book,authors);
                bookGetList.add(bookGet);
            }
            response.setBooks(bookGetList);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        conclude(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDeleteByIdResponse> deleteById(@PathVariable Long id) {
        BookDeleteByIdResponse response = new BookDeleteByIdResponse();
        prepare(response);

        Book book = new Book();
        book.setId(id);
        id = bookService.delete(book);

        if(id == null){
            response.setErrorMessage("Book not found.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else
            response.setId(id);

        conclude(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
