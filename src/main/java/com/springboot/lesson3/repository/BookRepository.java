package com.springboot.lesson3.repository;

import com.springboot.lesson3.model.Book;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface BookRepository extends CrudRepository<Book, Long> {

}
