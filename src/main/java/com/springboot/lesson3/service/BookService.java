package com.springboot.lesson3.service;

import com.springboot.lesson3.api.BookRequest;
import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BookService {

    final private BookRepository bookRepository;

    public void generateData() {
        bookRepository.saveAll(List.of(
                new Book("Война и Мира", "Л.Н.Толстой"),
                new Book("Метрвые Души", "Н.В.Гоголь"),
                new Book("Чистый Код", "Роберт Мартин")
        ));
    }

    public Book add(BookRequest request) {
        Book book = new Book(request.getName(), request.getAuthor());
        return bookRepository.save(book);
    }
    public List<Book> getAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).toList();
    }

    public Optional<Book> getByID(long id) {
        return bookRepository.findById(id);
    }

    public Book removeById(long id) throws NoBookException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoBookException(id));
        bookRepository.delete(book);
        return book;
    }
}
