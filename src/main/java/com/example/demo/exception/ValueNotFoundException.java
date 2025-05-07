package com.example.demo.exception;


public class ValueNotFoundException extends RuntimeException{
    private String message;
	public ValueNotFoundException(String message) {
		  super(message);
		this.message=message;
	}
}
