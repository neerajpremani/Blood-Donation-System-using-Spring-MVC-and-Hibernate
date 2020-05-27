package com.neu.bloodbank.exception;

public class DonorException extends Exception {


	public DonorException(String message) {
		super("DonorException : "+message);
	}

	public DonorException(String message,Throwable cause) {
		super("DonorException : "+message,cause);
	}

}
