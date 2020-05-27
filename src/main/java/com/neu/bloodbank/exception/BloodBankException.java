package com.neu.bloodbank.exception;

public class BloodBankException extends Exception {


	public BloodBankException(String message) {
		super("BloodBankException : "+message);
	}

	public BloodBankException(String message,Throwable cause) {
		super("BloodBankException : "+message,cause);
	}

}
