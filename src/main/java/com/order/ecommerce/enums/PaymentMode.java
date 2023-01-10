package com.order.ecommerce.enums;

public enum PaymentMode {

	CASH("CASH"),

	DEBIT("DEBIT"),

	CREDIT("CREDIT");

	private final String value;

	PaymentMode(String value) {
		this.value = value;
	}

	public String getString() {
		return value;
	}

}
