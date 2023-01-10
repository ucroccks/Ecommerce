package com.order.ecommerce.enums;

public enum PaymentStatus {
    
	PROCESSING("PROCESSING"),

    PAID("PAID"),

    REFUNDED("PAID");
    
    private final String value;

	PaymentStatus(String value) {
		this.value = value;
	}

	public String getString() {
		return value;
	}

}
