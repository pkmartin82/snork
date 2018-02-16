package com.pkm.snork;

public class SnorkRestClientException extends Exception {

	/** Serial Version UID */
	private static final long serialVersionUID = -2464955620744822179L;

	/**
	 * Default Constructor
	 */
	public SnorkRestClientException() {
		super();
	}

	/**
	 * Constructor with message
	 * 
	 * @param message
	 */
	public SnorkRestClientException(String message) {
		super(message);
	}

	/**
	 * Constructor with throwable
	 * 
	 * @param cause
	 */
	public SnorkRestClientException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor with message and throwable
	 * 
	 * @param message
	 * @param cause
	 */
	public SnorkRestClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
