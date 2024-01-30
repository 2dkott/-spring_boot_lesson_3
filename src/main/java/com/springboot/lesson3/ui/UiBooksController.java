package com.springboot.lesson3.ui;

import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ui/books")
public class UiBooksController {

    @Autowired
    private BookService bookService;
    @GetMapping()
    public String showBooks(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "books";
    }

}
