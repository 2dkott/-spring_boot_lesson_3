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

@Service
@RequiredArgsConstructor
public class IssuerService {

  private final BookRepository bookRepository;
  private final ReaderRepository readerRepository;
  private final IssueRepository issueRepository;
  private final BookLimitRetrieval bookLimitRetrieval;

  public Issue issue(IssueRequest request) throws NoBookException, NoReaderException, BookLimitExceededException {
    Optional<Reader> reader = Optional.ofNullable(readerRepository.getReaderById(request.getReaderId()));

    if (bookRepository.getBookById(request.getBookId()) == null) throw new NoBookException(request.getBookId());
    if (reader.isEmpty()) throw new NoReaderException(request.getReaderId());


    if (bookLimitRetrieval.doIssueBook(reader.get(), issueRepository.getIssuesByReaderId(reader.get().getId()))) {
      Issue issue = new Issue(request.getBookId(), request.getReaderId());
      return issueRepository.save(issue);
    }

    throw new BookLimitExceededException(reader.get().getId());
  }

  public Optional<Issue> returnIssue(IssueRequest request) {
    List<Issue> issues = issueRepository.getIssuesByReaderId(request.getReaderId());
    Optional<Issue> issue = issues.stream().filter(issue1 -> issue1.getBookId()==request.getBookId()).findFirst();
    issue.ifPresent(issue1 -> {
      issue1.setReturnDate(request.getReturnDate());
      issueRepository.updateReturnDatOfIssue(issue1);
    });
    return issue;
  }

  public List<Issue> getAllIssues() {
    return issueRepository.getAll();
  }

  public Optional<Book> getBookByIssue(Issue issue) {
    return Optional.ofNullable(bookRepository.getBookById(issue.getBookId()));
  }

  public Optional<Reader> getReaderByIssue(Issue issue) {
    return Optional.ofNullable(readerRepository.getReaderById(issue.getReaderId()));
  }

}
