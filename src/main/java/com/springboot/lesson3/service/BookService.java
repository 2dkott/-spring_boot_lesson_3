package com.springboot.lesson3.service;

import com.springboot.lesson3.api.BookRequest;
import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    final private BookRepository bookRepository;

    public Book add(BookRequest request) {
        Book book = new Book(request.getName(), request.getAuthor());
        return bookRepository.add(book);
    }
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    public Optional<Book> getByID(long id) {
        return Optional.ofNullable(bookRepository.getBookById(id));
    }

    public Optional<Book> removeById(long id) {
        Optional<Book> bookToDelete = Optional.ofNullable(bookRepository.getBookById(id));
        bookToDelete.ifPresent(bookRepository::removeBook);
        return bookToDelete;
    }
}
