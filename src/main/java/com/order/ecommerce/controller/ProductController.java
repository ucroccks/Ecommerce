package com.order.ecommerce.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.ecommerce.dto.ErrorDto;
import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.service.IProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

	private final IProductService productService;

	/**
	 * Creates a product
	 * 
	 * @param productDto
	 * @return
	 */
	@PostMapping
	@Operation(summary = "Create a product", description = "Create a product")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product created",
					content = @Content(schema = @Schema(implementation = ProductDto.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = @Content(schema = @Schema(implementation = ErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Exception",
					content = @Content(schema = @Schema(implementation = ErrorDto.class)))
	})
	public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
		return productService.createProduct(productDto);
	}

	/**
	 * Finds product by id
	 * 
	 * @param productId
	 * @return
	 */
	@GetMapping("/{productId}")
	@Operation(summary = "Find a product", description = "Find a product by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product found",
					content = @Content(schema = @Schema(implementation = ProductDto.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = @Content(schema = @Schema(implementation = ErrorDto.class))),
			@ApiResponse(responseCode = "404", description = "Product not found",
					content = @Content(schema = @Schema(implementation = ErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Exception",
					content = @Content(schema = @Schema(implementation = ErrorDto.class)))
	})
	public ProductDto findProductById(@NotEmpty(message = "Product Id cannot be null or empty") @PathVariable(
			name = "productId") String productId) {
		return productService.findProductById(productId);
	}

}
