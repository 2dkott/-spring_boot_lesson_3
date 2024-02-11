package com.springboot.lesson3.repository;

import com.springboot.lesson3.model.Issue;
import com.springboot.lesson3.model.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Long> {
  List<Issue> findIssuesByReader(Reader reader);
}
