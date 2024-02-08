package com.springboot.lesson3.api;

import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.model.Reader;
import com.springboot.lesson3.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Tag(name="Books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get all Books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book list is returned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during Book search",
                    content = @Content) })
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

    @Operation(summary = "Get a Book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Book is found",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
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


    @Operation(summary = "Create a Book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book was created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during Book creation",
                    content = @Content) })
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

    @Operation(summary = "Remove the Book by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book was deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during Book removing",
                    content = @Content) })
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
