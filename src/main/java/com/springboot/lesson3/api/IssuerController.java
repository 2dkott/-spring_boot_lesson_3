package com.springboot.lesson3.api;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.springboot.lesson3.model.Issue;
import com.springboot.lesson3.service.BookLimitExceededException;
import com.springboot.lesson3.service.IssuerService;
import com.springboot.lesson3.service.NoBookException;
import com.springboot.lesson3.service.NoReaderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/issue")
public class IssuerController {

  @Autowired
  private IssuerService service;

  @PostMapping
  public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) throws NoBookException, BookLimitExceededException, NoReaderException {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());
    return ResponseEntity.status(HttpStatus.OK).body(service.issue(request));
  }

  @PutMapping()
  public ResponseEntity<Issue> returnBook(@RequestBody IssueRequest request) {
    log.info("Получен запрос на возврат: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());
    Optional<Issue> issue = service.returnIssue(request);
    return issue.map(issue1 -> ResponseEntity.status(HttpStatus.FOUND).body(issue1)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
