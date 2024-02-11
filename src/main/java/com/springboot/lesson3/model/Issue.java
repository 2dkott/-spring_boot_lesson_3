package com.springboot.lesson3.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
@Data
public class Issue {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private  long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "books_id", nullable = false)
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private Book book;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "readers_id", nullable = false)
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private Reader reader;

  @Column(name = "issueDate")
  private LocalDateTime issuedDate;

  @Column(name = "returnDate")
  private LocalDateTime returnDate;

  public Issue(Book book, Reader reader, LocalDateTime returnDate) {
    this.book = book;
    this.reader = reader;
    this.issuedDate = LocalDateTime.now();
    this.returnDate = returnDate;
  }

  public Issue() {

  }
}
