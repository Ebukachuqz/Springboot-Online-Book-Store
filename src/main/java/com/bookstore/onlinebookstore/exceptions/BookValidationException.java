package com.bookstore.onlinebookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookValidationException extends RuntimeException {
    public BookValidationException(String message) {
        super(message);
    }
}
