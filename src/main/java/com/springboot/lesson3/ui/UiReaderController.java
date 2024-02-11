package com.springboot.lesson3.ui;

import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.model.Issue;
import com.springboot.lesson3.model.Reader;
import com.springboot.lesson3.service.BookService;
import com.springboot.lesson3.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ui/readers")
public class UiReaderController {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private BookService bookService;

    @GetMapping()
    public String showReaders(Model model) {
        List<Reader> readers = readerService.getAll();
        model.addAttribute("readers", readers);
        return "readers";
    }

    @GetMapping("/{id}")
    public String showReaders(@PathVariable long id, Model model) {
        Optional<Reader> reader = readerService.getByID(id);
        model.addAttribute("id", id);
        return reader.map(reader1 -> {
            List<Issue> issues = readerService.getAllIssues(reader1);
            List<Book> books = new ArrayList<>();
            issues.forEach(issue -> {
                Optional<Book> book = bookService.getByID(issue.getBook().getId());
                book.ifPresent(books::add);
            });
            model.addAttribute("reader", reader1);
            model.addAttribute("books", books);
            return "readerBook";
        }).orElse("readerNotFound");

    }
}