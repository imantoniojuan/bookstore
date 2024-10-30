package com.anthony.bookstore;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

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

import com.anthony.bookstore.entities.Author;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = BookstoreApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class AuthorControllerTests {

    @LocalServerPort
    int serverPort = 8081;

    Author createdAuthor;

    @BeforeAll
    public void setUp() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:" + serverPort + "/author/add");

        Author author = new Author("Unit Tester", LocalDate.now());
        HttpEntity<Author> request = new HttpEntity<>(author);
        ResponseEntity<Author> response = restTemplate.exchange(uri, HttpMethod.POST, request, Author.class);

        createdAuthor = response.getBody();

        Assertions.assertNotNull(createdAuthor);
        Assertions.assertNotNull(createdAuthor.getId());
    }

    @Test
    public void testModifyAuthor_withBody_thenSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:" + serverPort + "/author/modify");

        Author author = new Author(createdAuthor);
        author.setName("Unit Tester 2");

        HttpEntity<Author> request = new HttpEntity<>(author);
        ResponseEntity<Author> response = restTemplate.exchange(uri, HttpMethod.PUT, request, Author.class);

        Author responseAuthor = response.getBody();

        Assertions.assertNotNull(responseAuthor);
        Assertions.assertNotEquals(createdAuthor.getName(), responseAuthor.getName());

        createdAuthor = responseAuthor;
    }

    @Test
    public void testGetAuthor_withBody_thenSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:" + serverPort + "/author/" + createdAuthor.getId());

        ResponseEntity<Author> response = restTemplate.exchange(uri, HttpMethod.GET, null, Author.class);

        Author responseAuthor = response.getBody();

        Assertions.assertNotNull(responseAuthor);
        Assertions.assertEquals(createdAuthor.getName(), responseAuthor.getName());
    }

    @AfterAll
    public void tearDown() throws URISyntaxException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        // Step 1: Login to obtain token
        URI loginUri = new URI("http://localhost:" + serverPort + "/auth/login");

        // Set headers for login request
        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.set("Content-Type", "application/json");

        // Set login request body
        String loginRequestBody = """
            {
                "email": "test@email.com",
                "password": "pword124!"
            }
            """;

        HttpEntity<String> loginRequest = new HttpEntity<>(loginRequestBody, loginHeaders);
        ResponseEntity<String> loginResponse = restTemplate.exchange(loginUri, HttpMethod.POST, loginRequest, String.class);

        // Verify login response and extract token
        Assertions.assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        Assertions.assertNotNull(loginResponse.getBody());

        // Parse the JSON response to get the token
        JSONObject loginJson = new JSONObject(loginResponse.getBody());
        String token = loginJson.getString("token");
        Assertions.assertNotNull(token);

        // Step 2: Use the token to make an authorized DELETE request
        URI deleteUri = new URI("http://localhost:" + serverPort + "/author/" + createdAuthor.getId());

        // Set headers for DELETE request, including Bearer token
        HttpHeaders deleteHeaders = new HttpHeaders();
        deleteHeaders.set("Authorization", "Bearer " + token);

        HttpEntity<String> deleteRequest = new HttpEntity<>(deleteHeaders);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(deleteUri, HttpMethod.DELETE, deleteRequest, String.class);

        // Verify delete response
        Assertions.assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
    }
}
