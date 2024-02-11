package com.springboot.lesson3.repository;

import com.springboot.lesson3.model.LibUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<LibUser, Long> {
    Optional<LibUser> findByName(String name);
}
