package com.springboot.lesson3.api;

import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.model.Issue;
import com.springboot.lesson3.model.Reader;
import com.springboot.lesson3.repository.BookRepository;
import com.springboot.lesson3.repository.IssueRepository;
import com.springboot.lesson3.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class IssuerControllerTest extends ApiTest{

    @MockBean
    IssueRepository issueRepository;

    @MockBean
    BookRepository bookRepository;

    @MockBean
    ReaderRepository readerRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void issueTest() {

        Book book = new Book("Test Name 1", "Test Author 1");
        Reader reader = new Reader("Test Reader");

        when(readerRepository.findById(1l)).thenReturn(Optional.of(reader));
        when(bookRepository.findById(1l)).thenReturn(Optional.of(book));

        IssueRequest request = new IssueRequest();
        request.setBookId(1l);
        request.setReaderId(1l);

        Issue expectedIssue = new Issue(book, reader, LocalDateTime.now());

        when(issueRepository.save(any())).thenReturn(expectedIssue);

        Issue actualIssue = webTestClient.post()
                .uri("/issues")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Issue.class).returnResult().getResponseBody();
        assert expectedIssue.equals(actualIssue);
    }
}
