package com.springboot.lesson3.repository;

import com.springboot.lesson3.model.Issue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class IssueRepository {

  private final List<Issue> issues;

  public IssueRepository() {
    this.issues = new ArrayList<>();
  }

  public Issue save(Issue issue) {
    issues.add(issue);
    return issue;
  }

  public Issue updateReturnDatOfIssue(Issue updatedIssue) {
    Issue issueToUpdate = issues.stream().filter(issue -> issue.getId() == updatedIssue.getId()).findFirst().get();
    issueToUpdate.setReturnDate(updatedIssue.getReturnDate());
    return issueToUpdate;
  }

  public List<Issue> getIssuesByReaderId(long readerId) {
    return issues.stream().filter(issue -> issue.getReaderId()==readerId).toList();
  }

  public Issue getIssuesById(long id) {
    return issues.stream().filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElse(null);
  }

  public List<Issue> getAll() {
    return List.copyOf(issues);
  }

}
