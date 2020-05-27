package com.neu.bloodbank.exception;

public class BBStockException  extends Exception {


	public BBStockException(String message) {
		super("BBSTockException : " + message);
	}

	public BBStockException(String message,Throwable cause) {
		super("BBSTockException : " + message,cause);
	}

}
