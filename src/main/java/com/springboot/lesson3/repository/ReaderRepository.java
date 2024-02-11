package com.springboot.lesson3.repository;

import com.springboot.lesson3.model.Reader;
import org.springframework.data.repository.CrudRepository;

public interface ReaderRepository extends CrudRepository<Reader, Long> {
}
