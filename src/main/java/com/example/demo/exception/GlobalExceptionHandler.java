package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	  @ExceptionHandler(value=ValueNotFoundException.class)
	  @ResponseStatus(HttpStatus.CONFLICT)
	    public @ResponseBody ErrorMessage handleEmployeeException(ValueNotFoundException exception) {
	    	return new ErrorMessage(HttpStatus.CONFLICT.value(),exception.getMessage());
	    }

}
