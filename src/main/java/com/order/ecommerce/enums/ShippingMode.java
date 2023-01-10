package com.order.ecommerce.enums;

public enum ShippingMode {

	PICKUP("PICKUP"),

	DELIVERY("DELIVERY"),

	CURBSIDE_PICKUP("CURBSIDE_PICKUP");

	private final String value;

	ShippingMode(String value) {
		this.value = value;
	}

	public String getString() {
		return value;
	}

}
