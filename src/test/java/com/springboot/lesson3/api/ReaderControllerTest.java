package com.springboot.lesson3.api;

import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.model.Reader;
import com.springboot.lesson3.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


public class ReaderControllerTest extends ApiTest{

    @MockBean
    ReaderRepository readerRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void getAllTest() {
        List<Reader> readerList = new ArrayList<>();
        readerList.add(new Reader("Test Name 1"));
        readerList.add(new Reader("Test Name 2"));
        readerList.add(new Reader("Test Name 3"));

        when(readerRepository.findAll()).thenReturn(readerList);

        List<Reader> actualReader = webTestClient.get()
                .uri("/readers")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Reader>>() {
                }).returnResult().getResponseBody();
        assert readerList.equals(actualReader);
    }

    @Test
    public void getReaderByIdTest() {
        Reader expectedReader = new Reader("Test Name 1");
        when(readerRepository.findById(1L)).thenReturn(Optional.of(expectedReader));

        Reader actualReader = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/readers/{id}").build(1L))
                .exchange()
                .expectStatus().isFound()
                .expectBody(Reader.class).returnResult().getResponseBody();
        assert expectedReader.equals(actualReader);
    }

    @Test
    public void addReaderTest() {
        Reader expectedReader = new Reader("Test Name 1");
        ReaderRequest request = new ReaderRequest();
        request.setReaderName(expectedReader.getName());

        when(readerRepository.save(expectedReader)).thenReturn(expectedReader);

        Reader actualReader = webTestClient.post()
                .uri("/readers")
                .bodyValue(request).exchange()
                .expectStatus().isCreated()
                .expectBody(Reader.class).returnResult().getResponseBody();
        assert expectedReader.equals(actualReader);
    }
}
