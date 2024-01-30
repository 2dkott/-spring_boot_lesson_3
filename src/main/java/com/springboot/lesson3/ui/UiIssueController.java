package com.springboot.lesson3.ui;

import com.springboot.lesson3.model.Book;
import com.springboot.lesson3.model.Reader;
import com.springboot.lesson3.service.IssuerService;
import com.springboot.lesson3.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ui/issues")
public class UiIssueController {

    @Autowired
    private IssuerService issuerService;

    @GetMapping()
    public String showIssues(Model model) {
        List<IssueModel> issueModels = issuerService.getAllIssues().stream().map(issue -> {

            Book book = issuerService.getBookByIssue(issue).get();
            Reader reader = issuerService.getReaderByIssue(issue).get();
            return new IssueModel(issue,book,reader);

        }).toList();

        model.addAttribute("issues", issueModels);
        return "issues";
    }
}
