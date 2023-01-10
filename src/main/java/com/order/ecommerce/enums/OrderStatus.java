package com.order.ecommerce.enums;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatus {

	RECEIVED("RECEIVED"),

	PROCESSING("PROCESSING"),

	SHIPPED("SHIPPED"),

	COMPLETED("COMPLETED"),

	CANCELLED("CANCELLED"),

	REFUNDED("REFUNDED");

	private final String value;

	OrderStatus(String value) {
		this.value = value;
	}

	public String getString() {
		return value;
	}

	public static Optional<OrderStatus> getOrderStatus(String status) {
		return Arrays.stream(OrderStatus.values())
				.filter(orderStatus -> orderStatus.toString().equalsIgnoreCase(status)).findFirst();
	}

}
