package com.order.ecommerce.exception;

public class BadRequestException extends RequestException {

	private static final long serialVersionUID = 10l;

	public BadRequestException(String message) {
		super(message);
	}
}
