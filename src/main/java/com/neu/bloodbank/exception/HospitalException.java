package com.neu.bloodbank.exception;

public class HospitalException extends Exception {


	public HospitalException(String message) {
		super("HospitalException : "+message);
	}

	public HospitalException(String message,Throwable cause) {
		super("HospitalException : "+message,cause);
	}

}
