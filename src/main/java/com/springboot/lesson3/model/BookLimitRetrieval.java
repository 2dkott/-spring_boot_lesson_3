package com.springboot.lesson3.model;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class BookLimitRetrieval {

    final private int issuedBookLimits;
    public boolean doIssueBook(Reader reader, List<Issue> issuesList) {
        List<Issue> notReturnedIssues = issuesList.stream().filter(issue -> Objects.isNull(issue.getReturnDate())).toList();
        if (isCommonReader(reader) && notReturnedIssues.size() < issuedBookLimits) {
            return true;
        }
        return false;
    }

    //TODO:Just common logic
    public boolean isCommonReader(Reader reader) {
        return true;
    }
}
