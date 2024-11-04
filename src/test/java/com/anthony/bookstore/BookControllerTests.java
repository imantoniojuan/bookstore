package com.anthony.bookstore;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.json.JSONException;
import org.json.JSONObject;

import com.anthony.bookstore.dtos.requests.RegisterRequest;
import com.anthony.bookstore.dtos.responses.RegisterResponse;
import com.anthony.bookstore.entities.Author;
import com.anthony.bookstore.entities.Book;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = BookstoreApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class BookControllerTests {

    @LocalServerPort
    int serverPort = 8081;

    Book createdBook;

    List<Long> authorIds;
    Author createdAuthor;
    String testEmail = "test@email.com";
    String testPwd = "pword124!";

    @BeforeAll
    public void setUp() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:" + serverPort + "/auth/register");

        RegisterRequest rr = new RegisterRequest();
        rr.setEmail(testEmail);
        rr.setPassword(testPwd);
        HttpEntity<RegisterRequest> registrationRequest = new HttpEntity<>(rr);
        ResponseEntity<RegisterResponse> response = restTemplate.exchange(uri, HttpMethod.POST, registrationRequest, RegisterResponse.class);
        RegisterResponse rrsp = response.getBody();

        Assertions.assertNotNull(rrsp);

        uri = new URI("http://localhost:" + serverPort + "/author/add");

        Author author = new Author("Unit Tester", LocalDate.now());
        HttpEntity<Author> request = new HttpEntity<>(author);
        ResponseEntity<Author> authorresponse = restTemplate.exchange(uri, HttpMethod.POST, request, Author.class);

        createdAuthor = authorresponse.getBody();

        authorIds = new ArrayList<Long>();
        authorIds.add(createdAuthor.getId());

        uri = new URI("http://localhost:" + serverPort + "/book/add");

        Book book = new Book();
        book.setIsbn("999-1-00000-222-1");
        book.setTitle("Animal Stories");
        book.setPrice(19.99F);
        book.setGenre("Graphic Novels");
        book.setYear(2020);
        book.setAuthorIds(authorIds);

        HttpEntity<Book> bookRequest = new HttpEntity<>(book);
        ResponseEntity<Book> bookResponse = restTemplate.exchange(uri, HttpMethod.POST, bookRequest, Book.class);

        createdBook = bookResponse.getBody();

        Assertions.assertNotNull(createdBook);
        Assertions.assertNotNull(createdBook.getId());
    }

    @Test
    public void testModifyBook_withBody_thenSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:" + serverPort + "/book/modify");

        Book book = new Book(createdBook);
        book.setTitle("Animal Stories 2");
        book.setAuthorIds(authorIds);

        HttpEntity<Book> request = new HttpEntity<>(book);
        ResponseEntity<Book> response = restTemplate.exchange(uri, HttpMethod.PUT, request, Book.class);

        Book responseBook = response.getBody();

        Assertions.assertNotNull(responseBook);
        Assertions.assertNotEquals(createdBook.getTitle(), responseBook.getTitle());

        createdBook = responseBook;
    }

    @Test
    public void testGetBook_withBody_thenSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:" + serverPort + "/book/" + createdBook.getId());

        ResponseEntity<Book> response = restTemplate.exchange(uri, HttpMethod.GET, null, Book.class);

        Book responseBook = response.getBody();

        Assertions.assertNotNull(responseBook);
        Assertions.assertEquals(createdBook.getTitle(), responseBook.getTitle());
    }

    @AfterAll
    public void tearDown() throws URISyntaxException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        // Login to obtain token
        URI loginUri = new URI("http://localhost:" + serverPort + "/auth/login");

        // Set headers for login request
        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.set("Content-Type", "application/json");

        // Set login request body
        String loginRequestBody = """
            {
                "email": "%s",
                "password": "%s"
            }
            """.formatted(testEmail, testPwd);

        HttpEntity<String> loginRequest = new HttpEntity<>(loginRequestBody, loginHeaders);
        ResponseEntity<String> loginResponse = restTemplate.exchange(loginUri, HttpMethod.POST, loginRequest, String.class);

        // Verify login response and extract token
        Assertions.assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        Assertions.assertNotNull(loginResponse.getBody());

        // Parse the JSON response to get the token
        JSONObject loginJson = new JSONObject(loginResponse.getBody());
        String token = loginJson.getString("token");
        Assertions.assertNotNull(token);

        // Use the token to make an authorized DELETE request
        URI deleteUri = new URI("http://localhost:" + serverPort + "/book/" + createdBook.getId());

        // Set headers for DELETE request, including Bearer token
        HttpHeaders deleteHeaders = new HttpHeaders();
        deleteHeaders.set("Authorization", "Bearer " + token);

        HttpEntity<String> deleteRequest = new HttpEntity<>(deleteHeaders);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(deleteUri, HttpMethod.DELETE, deleteRequest, String.class);

        // Verify delete response
        Assertions.assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

        // Use the token to make an authorized DELETE request
        deleteUri = new URI("http://localhost:" + serverPort + "/author/" + createdAuthor.getId());

        deleteRequest = new HttpEntity<>(deleteHeaders);
        deleteResponse = restTemplate.exchange(deleteUri, HttpMethod.DELETE, deleteRequest, String.class);

        // Verify delete response
        Assertions.assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

    }
}
