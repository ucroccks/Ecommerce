package com.order.ecommerce.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.order.ecommerce.dto.ErrorDto;
import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.OrderResponseDto;
import com.order.ecommerce.service.IOrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final IOrderService orderService;

	/**
	 * Creates order
	 * 
	 * @param orderDto
	 * @return
	 */
	@PostMapping
	@Operation(summary = "Create an order", description = "Create an order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Order created",
					content = @Content(schema = @Schema(implementation = OrderResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = @Content(schema = @Schema(implementation = ErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Exception",
					content = @Content(schema = @Schema(implementation = ErrorDto.class)))
	})
	public OrderResponseDto createOrder(@Valid @RequestBody OrderDto orderDto) {
		return orderService.createOrder(orderDto);
	}

	/**
	 * Finds Order by Id
	 * 
	 * @param orderId
	 * @return
	 */
	@GetMapping("/{orderId}")
	@Operation(summary = "Find order", description = "Find order by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Order found",
					content = @Content(schema = @Schema(implementation = OrderDto.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = @Content(schema = @Schema(implementation = ErrorDto.class))),
			@ApiResponse(responseCode = "404", description = "Order not found",
					content = @Content(schema = @Schema(implementation = ErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Exception",
					content = @Content(schema = @Schema(implementation = ErrorDto.class)))
	})
	public OrderDto findOrderBy(
			@NotEmpty(message = "Order Id cannot be null or empty") @PathVariable(name = "orderId") String orderId) {
		return orderService.findOrderById(orderId);
	}

	/**
	 * Updates order status
	 * 
	 * @param orderId
	 * @param orderStatus
	 */
	@PatchMapping("/{orderId}")
	@Operation(summary = "Update order status", description = "Update order status")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Update Successful"),
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = @Content(schema = @Schema(implementation = ErrorDto.class))),
			@ApiResponse(responseCode = "404", description = "Order not found",
					content = @Content(schema = @Schema(implementation = ErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Exception",
					content = @Content(schema = @Schema(implementation = ErrorDto.class)))
	})
	public void updateOrderStatus(
			@NotEmpty(message = "Order Id cannot be null or empty") @PathVariable("orderId") String orderId,
			@NotEmpty(message = "Order Status cannot be null or empty") @RequestParam(
					name = "orderStatus") String orderStatus) {
		orderService.updateOrderStatus(orderId, orderStatus);
	}

}
