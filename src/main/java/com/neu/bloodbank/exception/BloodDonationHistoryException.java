package com.neu.bloodbank.exception;

public class BloodDonationHistoryException extends Exception {


	public BloodDonationHistoryException(String message) {
		super("BloodDonationHistoryException : "+ message);
	}

	public BloodDonationHistoryException(String message,Throwable cause) {
		super("BloodDonationHistoryException : "+message, cause);
	}

}
