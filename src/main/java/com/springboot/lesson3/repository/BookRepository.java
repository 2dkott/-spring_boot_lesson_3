package com.springboot.lesson3.repository;

import com.springboot.lesson3.model.Book;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository {

  private final List<Book> books;

  public BookRepository() {
    this.books = new ArrayList<>();
  }

  @PostConstruct
  public void generateData() {
    books.addAll(List.of(
      new Book("Война и Мира", "Л.Н.Толстой"),
      new Book("Метрвые Души", "Н.В.Гоголь"),
      new Book("Чистый Код", "Роберт Мартин")
    ));
  }

  public Book getBookById(long id) {
    return books.stream().filter(it -> Objects.equals(it.getId(), id))
      .findFirst()
      .orElse(null);
  }

  public Book add(Book book) {
    books.add(book);
    return book;
  }

  public List<Book> getAll() {
    return books;
  }

  public void removeBook(Book book) {
    books.remove(book);
  }

}
