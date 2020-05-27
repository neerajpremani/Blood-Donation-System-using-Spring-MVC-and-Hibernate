package com.neu.bloodbank.exception;

public class RequestBloodException extends Exception {


	public RequestBloodException(String message) {
		super("RequestBloodException : "+message);
	}

	public RequestBloodException(String message,Throwable cause) {
		super("RequestBloodException : "+message,cause);
	}

}
