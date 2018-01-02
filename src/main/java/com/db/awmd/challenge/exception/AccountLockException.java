package com.db.awmd.challenge.exception;

public class AccountLockException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AccountLockException(String message) {
		super(message);
	}

}
