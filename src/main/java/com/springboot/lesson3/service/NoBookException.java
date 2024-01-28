package com.springboot.lesson3.service;

public class NoBookException extends Exception{
    public NoBookException(long id) {
        super(String.format("No book with id:%s", id));
    }
}
