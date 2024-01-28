package com.springboot.lesson3.api;

import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books;

        try {
            books = bookService.getAll();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable long id) {
        Optional<Book> book;
        try {
            book = bookService.getByID(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return book.map(book1 -> ResponseEntity.status(HttpStatus.FOUND).body(book1)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookRequest request) {
        Book createdBook;

        try {
            createdBook = bookService.add(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> addBook(@PathVariable long id) {
        Optional<Book> book;
        try {
            book = bookService.removeById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return book.map(book1 -> ResponseEntity.status(HttpStatus.OK).body(book1)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
