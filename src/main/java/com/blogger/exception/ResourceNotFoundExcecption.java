package com.blogger.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// it acts as try block it throws exception
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundExcecption extends  RuntimeException {

    private String Messsage;

    public ResourceNotFoundExcecption(String messsage) {

        super(messsage);
    }
}
