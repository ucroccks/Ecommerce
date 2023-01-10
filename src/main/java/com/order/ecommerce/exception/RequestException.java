package com.order.ecommerce.exception;

public class RequestException extends RuntimeException {
	
	private static final long serialVersionUID = 10l;
	
	public RequestException(String message) {
		super(message, null, false, false);
	}
} 