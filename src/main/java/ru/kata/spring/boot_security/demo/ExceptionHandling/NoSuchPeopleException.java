package ru.kata.spring.boot_security.demo.ExceptionHandling;

public class NoSuchPeopleException extends RuntimeException {
    public NoSuchPeopleException(String message) {
        super(message);
    }
}
