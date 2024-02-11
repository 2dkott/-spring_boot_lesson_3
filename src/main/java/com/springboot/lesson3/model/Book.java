package com.springboot.lesson3.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "author")
  private String author;

  public Book(String name, String author) {
    this.name = name;
    this.author = author;
  }

  public Book() {

  }
}
