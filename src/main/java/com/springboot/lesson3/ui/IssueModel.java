package com.springboot.lesson3.ui;

import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.model.Issue;
import com.springboot.lesson3.model.Reader;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IssueModel {
    private Issue issue;
    private Book book;
    private Reader reader;
}
