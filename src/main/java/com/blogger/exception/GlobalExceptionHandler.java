package com.blogger.exception;

import com.blogger.payload.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

// it acts as catch block
@ControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
@ExceptionHandler(ResourceNotFoundExcecption.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(

            ResourceNotFoundExcecption excecption,
            WebRequest webRequest
    ){
    ErrorDetails errorDetails = new ErrorDetails(new Date(), excecption.getMessage(), webRequest.getDescription(true));

    return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(InvalidPathException.class)
    public ResponseEntity<ErrorDetails> invalidPathException(
            InvalidPathException pathexception,
            WebRequest webRequest

    ){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), pathexception.getMessage(), webRequest.getDescription(true));
       return new ResponseEntity<>(errorDetails,INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataAlreadyExists.class)
    public ResponseEntity<ErrorDetails> dataalreadyexists(
            DataAlreadyExists dataAlreadyExists,
            WebRequest webRequest

    ){
    ErrorDetails errorDetails = new ErrorDetails(new Date(),dataAlreadyExists.getMessage(), webRequest.getDescription(true));
    return new ResponseEntity<>(errorDetails,INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> blogapiexception(
            BlogAPIException blogApiException,
            WebRequest webRequest
    ){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),blogApiException.getMessage(), webRequest.getDescription(true));
        return new ResponseEntity<>(errorDetails,INTERNAL_SERVER_ERROR);
    }
}
