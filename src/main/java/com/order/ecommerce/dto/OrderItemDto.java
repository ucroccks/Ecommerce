package com.order.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class OrderItemDto {

    @NotNull(message = "Product Id must not be null")
    private final String productId;
    @NotNull
    @Min(value = 1, message = "Minimum quantity error.")
    private final Integer quantity;
}
