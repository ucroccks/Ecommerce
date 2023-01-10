package com.order.ecommerce.exception;

public class InternalServerErrorException extends RequestException {
	
	private static final long serialVersionUID = 10l;

	public InternalServerErrorException(String message) {
		super(message);
	}
}
