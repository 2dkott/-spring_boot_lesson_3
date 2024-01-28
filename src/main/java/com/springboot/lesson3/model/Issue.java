package com.springboot.lesson3.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
public class Issue {

  public static long sequence = 1L;

  private final long id;
  private final long bookId;
  private final long readerId;
  private final LocalDateTime issuedDate;
  private LocalDateTime returnDate;

  public Issue(long bookId, long readerId) {
    this.id = sequence++;
    this.bookId = bookId;
    this.readerId = readerId;
    this.issuedDate = LocalDateTime.now();
  }

  public Issue(long bookId, long readerId, LocalDateTime returnDate) {
    this.id = sequence++;
    this.bookId = bookId;
    this.readerId = readerId;
    this.issuedDate = LocalDateTime.now();
    this.returnDate = returnDate;
  }

}
