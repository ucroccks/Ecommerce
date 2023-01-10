package com.order.ecommerce.exception;

public class NotFoundException extends RequestException {
	
	private static final long serialVersionUID = 10l;
	
	public NotFoundException(String message) {
		super(message);
	}
}
