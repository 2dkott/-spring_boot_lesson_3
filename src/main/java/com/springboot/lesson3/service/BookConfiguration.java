package com.springboot.lesson3.service;

import com.springboot.lesson3.model.BookLimitRetrieval;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfiguration {

    @Value("${application.max-allowed-books}")
    private int maxBooksLimit;

    @Bean
    public BookLimitRetrieval getBookingLimits () {
        return new BookLimitRetrieval(maxBooksLimit);
    }
}
