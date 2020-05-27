package com.neu.bloodbank.exception;

public class SuperRoleException extends Exception {


	public SuperRoleException(String message) {
		super("SuperRoleException : "+message);
	}

	public SuperRoleException(String message,Throwable cause) {
		super("SuperRoleException : "+cause);
	}

}
