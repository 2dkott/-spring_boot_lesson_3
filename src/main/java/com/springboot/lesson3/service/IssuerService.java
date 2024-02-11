package com.springboot.lesson3.service;

import com.springboot.lesson3.api.IssueRequest;
import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.model.BookLimitRetrieval;
import com.springboot.lesson3.model.Issue;
import com.springboot.lesson3.model.Reader;
import com.springboot.lesson3.repository.BookRepository;
import com.springboot.lesson3.repository.IssueRepository;
import com.springboot.lesson3.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class IssuerService {

  private final BookRepository bookRepository;
  private final ReaderRepository readerRepository;
  private final IssueRepository issueRepository;
  private final BookLimitRetrieval bookLimitRetrieval;

  public Issue issue(IssueRequest request) throws NoBookException, NoReaderException, BookLimitExceededException {
    Reader reader = readerRepository.findById(request.getReaderId()).orElseThrow(() -> new NoReaderException(request.getReaderId()));;
    Book book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new NoBookException(request.getBookId()));

    if (bookLimitRetrieval.doIssueBook(reader, issueRepository.findIssuesByReader(reader))) {
      Issue issue = new Issue(book, reader, null);
      return issueRepository.save(issue);
    }

    throw new BookLimitExceededException(reader.getId());
  }

  public Optional<Issue> returnIssue(IssueRequest request) throws NoReaderException {
    Reader reader = readerRepository.findById(request.getReaderId()).orElseThrow(() -> new NoReaderException(request.getReaderId()));;

    List<Issue> issues = issueRepository.findIssuesByReader(reader);
    Optional<Issue> issue = issues.stream().filter(issue1 -> issue1.getBook().getId()==request.getBookId()).findFirst();
    issue.ifPresent(issue1 -> {
      issue1.setReturnDate(request.getReturnDate());
      issueRepository.save(issue1);
    });
    return issue;
  }

  public List<Issue> getAllIssues() {
    return StreamSupport.stream(issueRepository.findAll().spliterator(), false).toList();
  }

  public Optional<Book> getBookByIssue(Issue issue) {
    return bookRepository.findById(issue.getBook().getId());
  }

  public Optional<Reader> getReaderByIssue(Issue issue) {
    return readerRepository.findById(issue.getReader().getId());
  }

}
