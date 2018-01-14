package com.nemo.pool;

public class ConnectionException extends RuntimeException {

	private static final long serialVersionUID = -4568975632145689654L;

	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
