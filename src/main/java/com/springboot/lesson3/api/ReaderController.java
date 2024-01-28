package com.springboot.lesson3.api;

import com.springboot.lesson3.model.Issue;
import com.springboot.lesson3.model.Reader;
import com.springboot.lesson3.service.IssuerService;
import com.springboot.lesson3.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    private ReaderService readerService;


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

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable long id) {
        Optional<Reader> reader;
        try {
            reader = readerService.getByID(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return reader.map(reader1 -> ResponseEntity.status(HttpStatus.FOUND).body(reader1)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


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

    @DeleteMapping("/{id}")
    public ResponseEntity<Reader> deleteReader(@PathVariable long id) {
        Optional<Reader> reader;
        try {
            reader = readerService.removeById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return reader.map(reader1 -> ResponseEntity.status(HttpStatus.OK).body(reader1)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

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
