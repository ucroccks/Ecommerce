package com.order.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderDto {

	@NotEmpty(message = "customer id cannot be null or empty.")
	private final String customerId;

	private final BigDecimal subTotal;

	private final BigDecimal totalAmt;

	private final BigDecimal tax;

	private final BigDecimal shippingCharges;

	@NotEmpty(message = "title cannot be null or empty.")
	private final String title;

	private final String shippingMode;

	private final BigDecimal amount;

	@NotEmpty(message = "payment mode cannot be null or empty.")
	private final String paymentMode;

	@Valid
	@NotNull(message = "billing address cannot be null.")
	private final AddressDto billingAddress;

	private final AddressDto shippingAddress;

	@Valid
	@NotEmpty(message = "order items cannot be null or empty.")
	private final List<OrderItemDto> orderItems;

	@NotEmpty(message = "order status cannot be null or empty.")
	private final String orderStatus;
}
