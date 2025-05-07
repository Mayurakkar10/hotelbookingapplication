package com.example.demo.exception;

import lombok.Data;

@Data
public class ErrorMessage {
private int statuscode;
private String message;
public ErrorMessage(int statuscode,String message) {
	this.message=message;
	this.statuscode=statuscode;
}
}
