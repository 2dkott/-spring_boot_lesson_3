package com.springboot.lesson3.repository;

import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.model.Reader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ReaderRepository {

  private final List<Reader> readers;

  public ReaderRepository() {
    this.readers = new ArrayList<>();
  }

  @PostConstruct
  public void generateData() {
    readers.addAll(List.of(
      new Reader("Игорь")
    ));
  }

  public Reader getReaderById(long id) {
    return readers.stream().filter(it -> Objects.equals(it.getId(), id))
      .findFirst()
      .orElse(null);
  }

  public Reader add(Reader reader) {
    readers.add(reader);
    return reader;
  }

  public List<Reader> getAll() {
    return readers;
  }

  public void removeReader(Reader reader) {
    readers.remove(reader);
  }

}
