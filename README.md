# Bookstore POC 

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [MySQL 8.0](https://dev.mysql.com/downloads/mysql/8.0.html)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.anthony.bookstore.BookstoreApplication` class from your IDE.

Second, you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```
mvn spring-boot:run
```

Lastly, you can compile the jar file and run it like so:

```
mvn install
java -jar target/bookstore-0.0.1-SNAPSHOT.jar
```

## About the Service

The service is just a simple bookstore REST service. It uses an MySQL to store the data. Once database connection works, REST endpoints are on **port 8080**. (see below)

Here is what this application demonstrates: 

* Full integration with the latest **Spring** Framework: inversion of control, dependency injection, etc.
* Packaging as a single jar and just run using the ``java -jar`` command
* Writing a RESTful service using annotation: supports JSON request / response;
* *Spring Data* Integration with JPA/Hibernate with configuration and familiar annotations. 
* Automatic CRUD functionality against the data source using Spring *Repository* pattern
* Demonstrates SpringBootTest test framework with associated libraries 


Here is an overview of the application's APIs:

* All APIs except DELETE methods are allowed without authentication
* Anyone can register for now then subsequently login and use their token to call DELETE methods
* An author record cannot be deleted if it has a book record(s) attached to it, either delete the book record first or modify to remove the author
* When creating a book record, at least 1 author is required
* An author can be created without any dependencies


Here are some endpoints available:

### Auth APIs

```
POST /auth/register HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
    "email":"test@email.com",
    "password":"*******"
}

RESPONSE: HTTP 200
Location header: http://localhost:8080/auth/register
```

```
POST /auth/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
    "email":"test@email.com",
    "password":"*******"
}

RESPONSE: HTTP 200
{
  "statusCode": 200,
  "token": "eyJhbGciOiJIUzM4N3J9.eyJzdWIiOiJ0ZXN0QGVtYqlsLmNvbSIsImlhdCI6MTczMDI5MDUxMiwiZXhwIjoxNzMwMjk0MTEyfQ.TJbHQ1aC5FzZIwj3yvsz5MnpEDSb4opqyrqoelzC2ZlkxaYA8fV_ytGHEPpf1cm8",
  "expiresIn": 3600000
}
Location header: http://localhost:8080/auth/login
```

### Author APIs
#### Add Author
```
POST /author/add HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 58

{
    "name":"Ben Kinsley2",
    "birthday": "2000-03-01"
}

RESPONSE: HTTP 200
{
    "statusCode": 200,
    "id": 2,
    "name": "Ben Kinsley2",
    "birthday": "2000-03-01",
    "books": []
}
Location header: http://localhost:8080/author/add
```
#### Modify Author
```
PUT /author/modify HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 72

{
    "id": 2,
    "name":"Ben Kinsley2",
    "birthday": "2000-03-01"
}

RESPONSE: HTTP 200
{
    "statusCode": 200,
    "id": 2,
    "name": "Ben Kinsley2",
    "birthday": "2000-03-01",
    "books": []
}
Location header: http://localhost:8080/author/modify
```
#### Get Author by ID
```
GET /author/2 HTTP/1.1
Host: localhost:8080

RESPONSE: HTTP 200
{
    "statusCode": 200,
    "id": 2,
    "name": "Ben Kinsley2",
    "birthday": "2000-03-01",
    "books": []
}
Location header: http://localhost:8080/author/2
```
#### Delete Author by ID
```
DELETE /author/2 HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzM4N3J9.eyJzdWIiOiJ0ZXN0QGVtYqlsLmNvbSIsImlhdCI6MTczMDI5MDUxMiwiZXhwIjoxNzMwMjk0MTEyfQ.TJbHQ1aC5FzZIwj3yvsz5MnpEDSb4opqyrqoelzC2ZlkxaYA8fV_ytGHEPpf1cm8

RESPONSE: HTTP 200
{
    "statusCode": 200,
    "id": 2
}
Location header: http://localhost:8080/author/2
```
#### Get Author by Filters & Pagination
```
GET /author/?query=Kinsley&offset=0&limit=1 HTTP/1.1
Host: localhost:8080

RESPONSE: HTTP 200
{
    "statusCode": 200,
    "authors": [
        {
            "id": 2,
            "name": "Ben Kinsley2",
            "birthday": "2000-03-01",
            "books": [
                {
                    "id": 2,
                    "isbn": "978-1-60309-502-0",
                    "title": "Animal Stories",
                    "year": 2021,
                    "price": 19.99,
                    "genre": "Graphic Novels"
                }
            ]
        }
    ],
    "pagination": {
        "offset": 0,
        "limit": 1,
        "numOfItems": 1,
        "totalPages": 1,
        "totalItems": 1
    }
}
Location header: http://localhost:8080/author/?query=Kinsley&offset=0&limit=1
```

### Book APIs
#### Add Book
```
POST /book/add HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 142

{
    "isbn":"978-1-60309-502-1",
    "title": "Animal Stories 2",
    "price": 19.99,
    "genre": "Graphic Novels",
    "authorIds": [2,3]
}

RESPONSE: HTTP 200
{
    "statusCode": 200,
    "id": 3,
    "isbn": "978-1-60309-502-1",
    "title": "Animal Stories 2",
    "price": 19.99,
    "genre": "Graphic Novels",
    "authors": [
        {
            "id": 2,
            "name": "Ben Kinsley2",
            "birthday": "2000-03-01"
        },
        {
            "id": 3,
            "name": "Ben Kinsley",
            "birthday": "2000-03-01"
        }
    ]
}
Location header: http://localhost:8080/book/add
```
#### Modify Book
```
PUT /book/modify HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 169

{
    "id": 2,
    "isbn":"978-1-60309-502-0",
    "title": "Animal Stories",
    "price": 19.99,
    "genre": "Graphic Novels",
    "authorIds": [2],
    "year": 2021
}

RESPONSE: HTTP 200
{
    "statusCode": 200,
    "id": 2,
    "isbn": "978-1-60309-502-0",
    "title": "Animal Stories",
    "year": 2021,
    "price": 19.99,
    "genre": "Graphic Novels",
    "authors": [
        {
            "id": 2,
            "name": "Ben Kinsley2",
            "birthday": "2000-03-01"
        }
    ]
}
Location header: http://localhost:8080/book/modify
```
#### Get Book by ID
```
GET /book/2 HTTP/1.1
Host: localhost:8080

RESPONSE: HTTP 200
{
    "statusCode": 200,
    "id": 2,
    "isbn": "978-1-60309-502-0",
    "year": 2021,
    "price": 19.99,
    "genre": "Graphic Novels",
    "authors": [
        {
            "id": 2,
            "name": "Ben Kinsley2",
            "birthday": "2000-03-01"
        }
    ]
}
Location header: http://localhost:8080/book/2
```
#### Delete Book by ID
```
DELETE /book/2 HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzM4N3J9.eyJzdWIiOiJ0ZXN0QGVtYqlsLmNvbSIsImlhdCI6MTczMDI5MDUxMiwiZXhwIjoxNzMwMjk0MTEyfQ.TJbHQ1aC5FzZIwj3yvsz5MnpEDSb4opqyrqoelzC2ZlkxaYA8fV_ytGHEPpf1cm8

RESPONSE: HTTP 200
{
    "statusCode": 200,
    "id": 2
}
Location header: http://localhost:8080/book/2
```
#### Get Book by Filters & Pagination
```
GET /book/?query=animal&offset=0&limit=1 HTTP/1.1
Host: localhost:8080

RESPONSE: HTTP 200
{
    "statusCode": 200,
    "books": [
        {
            "id": 2,
            "isbn": "978-1-60309-502-0",
            "title": "Animal Stories",
            "year": 2021,
            "price": 19.99,
            "genre": "Graphic Novels",
            "authors": [
                {
                    "id": 2,
                    "name": "Ben Kinsley2",
                    "birthday": "2000-03-01"
                }
            ]
        }
    ],
    "pagination": {
        "offset": 0,
        "limit": 1,
        "numOfItems": 1,
        "totalPages": 1,
        "totalItems": 1
    }
}
Location header: http://localhost:8080/book/?query=animal&offset=0&limit=1
```

## Running unit tests

Unit tests can be run like so:

```
mvn test
```

## Future implementations

Some features that could be implemented:

* Make use of user permission table to handle authorization to APIs via path and method (i.e. User 1 may have access to POST APIs in /book/add and /author/add but User 2 may only have access to DELETE APIs to delete book and author records.
* Make use of an SMTP service to send a registation OTP upon registration
* Refined author searches as author search is only by name
* Refined book searches as book search is a query that will add any records with matching query based on ISBN, Title, Genre and Year, (i.e. a book with title 'Horror Stories' and a book with a different title but genre is 'Horror' will show up in search results)
* Make use of any caching mechanisms for optimizations on scaling


## Database overview
![Database overview](./docs/db.png)