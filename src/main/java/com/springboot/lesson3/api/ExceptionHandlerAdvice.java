package com.springboot.lesson3.api;

import com.springboot.lesson3.service.BookLimitExceededException;
import com.springboot.lesson3.service.NoBookException;
import com.springboot.lesson3.service.NoReaderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BookLimitExceededException.class)
    public ResponseEntity handleBookLimitException(BookLimitExceededException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler(NoBookException.class)
    public ResponseEntity handleNOBookException(NoBookException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(NoReaderException.class)
    public ResponseEntity handleNoReaderException(NoReaderException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
