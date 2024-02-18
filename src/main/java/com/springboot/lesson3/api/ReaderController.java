package com.springboot.lesson3.api;

import com.springboot.lesson3.model.Issue;
import com.springboot.lesson3.model.Reader;
import com.springboot.lesson3.service.IssuerService;
import com.springboot.lesson3.service.NoBookException;
import com.springboot.lesson3.service.NoReaderException;
import com.springboot.lesson3.service.ReaderService;
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
@RequestMapping("/readers")
@Tag(name="Readers")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @Operation(summary = "Get all Readers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reader list is returned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during Reader search",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<List<Reader>> getAllReader() {
        List<Reader> readers;

        try {
            readers = readerService.getAll();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(readers);
    }

    @Operation(summary = "Get a Reader by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Reader is found",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Reader not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable("id") long id) {
        Optional<Reader> reader;
        try {
            reader = readerService.getByID(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return reader.map(reader1 -> ResponseEntity.status(HttpStatus.FOUND).body(reader1)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @Operation(summary = "Create a Reader")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reader was created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during Reader creation",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestBody ReaderRequest readerRequest) {
        Reader createdReader;

        try {
            createdReader = readerService.add(readerRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdReader);
    }

    @Operation(summary = "Remove the Reader by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reader was deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "404", description = "Reader not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during Reader removing",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<Reader> deleteReader(@PathVariable long id) {
        Reader reader;
        try {
            reader = readerService.removeById(id);
        } catch (NoReaderException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(reader);
    }

    @Operation(summary = "Get Issues of the Reader by  Reader Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reader was deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "404", description = "Reader not found",
                    content = @Content),
            @ApiResponse(responseCode = "302", description = "Reader was found and issue list is returned",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during Reader removing",
                    content = @Content) })

    @GetMapping("/{id}/issue")
    public ResponseEntity<List<Issue>> getIssues(@PathVariable long id) {
        Optional<Reader> reader;
        try {
            reader = readerService.getByID(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return reader.map(reader1 -> ResponseEntity.status(HttpStatus.FOUND).body(readerService.getAllIssues(reader1))).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

}
