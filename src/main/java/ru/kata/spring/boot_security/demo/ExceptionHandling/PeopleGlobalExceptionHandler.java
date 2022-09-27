package ru.kata.spring.boot_security.demo.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PeopleGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<PeopleIncorrectData> handlerException(NoSuchPeopleException noSuchPeopleException) {
        PeopleIncorrectData data = new PeopleIncorrectData();
        data.setInfo(noSuchPeopleException.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
}
