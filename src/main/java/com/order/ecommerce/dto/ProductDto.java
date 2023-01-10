package com.order.ecommerce.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

	@NotEmpty(message = "Product Id cannot be null or empty.")
	private final String productId;

	@NotEmpty(message = "Product sku cannot be null or empty.")
	private final String sku;

	@NotEmpty(message = "Product title cannot be null or empty.")
	private final String title;

	@NotEmpty(message = "Product description cannot be null or empty.")
	private final String description;

	private final double price;
}
