package com.order.ecommerce.enums;


public enum ExceptionMessages {

	INTERNAL_SERVER_ERROR("INTERNAL SERVER ERROR");

	private final String value;

	ExceptionMessages(String value) {
		this.value = value;
	}

	public String getString() {
		return value;
	}
}
