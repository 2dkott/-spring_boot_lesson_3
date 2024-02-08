package com.springboot.lesson3.api;

import com.springboot.lesson3.model.Issue;
import com.springboot.lesson3.model.Reader;
import com.springboot.lesson3.service.BookLimitExceededException;
import com.springboot.lesson3.service.IssuerService;
import com.springboot.lesson3.service.NoBookException;
import com.springboot.lesson3.service.NoReaderException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/issues")
@Tag(name="Issues")
public class IssuerController {

  @Autowired
  private IssuerService service;

  @Operation(summary = "Create a Issue")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Issue was created",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Reader.class)) }) })
  @PostMapping
  public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) throws NoBookException, BookLimitExceededException, NoReaderException {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());
    return ResponseEntity.status(HttpStatus.OK).body(service.issue(request));
  }

  @Operation(summary = "Update Issues with return date and time")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "404", description = "Issue not found",
                  content = @Content),
          @ApiResponse(responseCode = "302", description = "Reader was found and updated",
                  content = @Content) })
  @PutMapping()
  public ResponseEntity<Issue> returnBook(@RequestBody IssueRequest request) {
    log.info("Получен запрос на возврат: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());
    Optional<Issue> issue = service.returnIssue(request);
    return issue.map(issue1 -> ResponseEntity.status(HttpStatus.FOUND).body(issue1)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
