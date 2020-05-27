package com.neu.bloodbank.exception;

public class EmailException extends Exception {


	public EmailException(String message) {
		super("EmailException : "+message);
	}

	public EmailException(String message,Throwable cause) {
		super("EmailException : "+message,cause);
	}

}
