package com.springboot.lesson3.service;

public class BookLimitExceededException extends Exception{
    public BookLimitExceededException(long readerId) {
        super(String.format("Book limit is exceeded for reader with id:%s", readerId));
    }
}
