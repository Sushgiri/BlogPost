package com.blogger.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataAlreadyExists  extends RuntimeException{

    private String message;

    public DataAlreadyExists(String message)  {
        super(message);
    }
}
