package com.springboot.lesson3.api;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Запрос на выдачу
 */
@Data
public class IssueRequest {

  /**
   * Идентификатор читателя
   */
  private long readerId;

  /**
   * Идентификатор книги
   */
  private long bookId;
  private LocalDateTime returnDate;

}
