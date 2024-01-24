package com.blogger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlogAPIException extends RuntimeException{
    private String message;
    public BlogAPIException(HttpStatus badRequest, String message) {
        super(message);
    }
}
