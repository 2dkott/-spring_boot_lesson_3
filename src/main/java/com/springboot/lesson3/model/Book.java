package com.springboot.lesson3.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Book {

  public static long sequence = 1L;

  private final long id;
  private final String name;
  private final String author;

  public Book(String name, String author) {
    this(sequence++, name, author);
  }

}
