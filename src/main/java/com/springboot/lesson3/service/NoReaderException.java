package com.springboot.lesson3.service;

public class NoReaderException extends Exception{
    public NoReaderException(long id) {
        super(String.format("No reader with id:%s", id));
    }
}
