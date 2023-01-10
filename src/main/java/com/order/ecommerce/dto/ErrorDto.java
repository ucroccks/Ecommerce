package com.order.ecommerce.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorDto {

	private final LocalDateTime timestamp;

	private final int status;

	private final String error;

	private final String message;

}
