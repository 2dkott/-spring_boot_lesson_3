package com.springboot.lesson3.api;

import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


public class BookControllerTest extends ApiTest{

    @MockBean
    BookRepository bookRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void getAllTest() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("Test Name 1", "Test Author 1"));
        bookList.add(new Book("Test Name 2", "Test Author 2"));
        bookList.add(new Book("Test Name 2", "Test Author 2"));

        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> actualBooks = webTestClient.get()
                .uri("/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Book>>() {
                }).returnResult().getResponseBody();
        assert bookList.equals(actualBooks);
    }

    @Test
    public void getByIdTest() {
        Book expectedBook = new Book("Test Name 1", "Test Author 1");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(expectedBook));

        Book actualBook = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/books/{id}").build(1L))
                .exchange()
                .expectStatus().isFound()
                .expectBody(Book.class).returnResult().getResponseBody();
        assert actualBook.equals(expectedBook);
    }

    @Test
    public void addBookTest() {
        Book expectedBook = new Book("Test Name 1", "Test Author 1");
        BookRequest request = new BookRequest();
        request.setName(expectedBook.getName());
        request.setAuthor(expectedBook.getAuthor());

        when(bookRepository.save(expectedBook)).thenReturn(expectedBook);

        Book actualBook = webTestClient.post()
                .uri("/books")
                .bodyValue(request).exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).returnResult().getResponseBody();
        assert actualBook.equals(expectedBook);
    }
}
