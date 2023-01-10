package com.order.ecommerce.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.order.ecommerce.dto.AddressDto;
import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.OrderItemDto;
import com.order.ecommerce.dto.OrderResponseDto;
import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.Address;
import com.order.ecommerce.entity.Order;
import com.order.ecommerce.entity.OrderItem;
import com.order.ecommerce.entity.OrderItemPk;
import com.order.ecommerce.entity.Payment;
import com.order.ecommerce.enums.OrderStatus;
import com.order.ecommerce.enums.PaymentStatus;
import com.order.ecommerce.exception.BadRequestException;
import com.order.ecommerce.exception.NotFoundException;
import com.order.ecommerce.mapper.OrderDetailsMapper;
import com.order.ecommerce.repository.IAddressRepository;
import com.order.ecommerce.repository.IOrderItemRepository;
import com.order.ecommerce.repository.IOrderRepository;
import com.order.ecommerce.repository.IPaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

	private final IOrderRepository orderRepository;
	private final IOrderItemRepository orderItemRepository;
	private final IPaymentRepository paymentRepository;
	private final IAddressRepository addressRepository;

	private final IProductService productService;
	private final OrderDetailsMapper orderDetailsMapper = Mappers.getMapper(OrderDetailsMapper.class);

	@Override
	@Transactional
	public OrderResponseDto createOrder(OrderDto orderDto) {
		log.info("Creating Order for customer = {}", orderDto.getCustomerId());

		log.info("Verifying all products exists before generating order");
		List<String> productIds =
				orderDto.getOrderItems().stream().map(OrderItemDto::getProductId).distinct().toList();
		List<ProductDto> products = productService.findAllById(productIds);
		if (products == null || products.isEmpty() || products.size() != productIds.size()) {
			log.info("Not all product(s) exist, failed to create order!");
			throw new NotFoundException("Not all product(s) exist, failed to create order!");
		}

		Order order = generateOrder(orderDto);
		log.info("Generated order for orderId = {}", order.getOrderId());

		Order savedOrder = orderRepository.save(order);
		String savedOrderId = savedOrder.getOrderId();
		List<OrderItem> orderItemList = buildOrderItems(orderDto.getOrderItems(), savedOrderId);
		orderItemRepository.saveAll(orderItemList);

		log.info("Successfully saved order & order items with id = {} for customer = {} on {}", savedOrder.getOrderId(),
				savedOrder.getCustomerId(), savedOrder.getCreatedAt());

		return OrderResponseDto.builder()
				.orderId(savedOrderId)
				.orderStatus(savedOrder.getOrderStatus())
				.build();
	}

	@Override
	public OrderDto findOrderById(String orderId) {
		log.info("Finding order for orderId = {}", orderId);
		Optional<Order> order = orderRepository.findById(orderId);
		if (order.isEmpty()) {
			log.info("Cannot find order with id = {}", orderId);
			throw new NotFoundException("Cannot find order with id = " + orderId);
		}

		log.info("Successfully found order for orderId = {}", orderId);
		return orderDetailsMapper.toOrderDto(order.get());
	}

	@Override
	public void updateOrderStatus(String orderId, String status) {
		OrderDto orderDto = findOrderById(orderId);

		if (orderDto == null) {
			log.info("Cannot update status for orderId = {}", orderId);
			throw new BadRequestException("Cannot update status for orderId = " + orderId);
		}

		Optional<OrderStatus> optOrderStatus = OrderStatus.getOrderStatus(status);

		if (optOrderStatus.isEmpty()) {
			log.error("Invalid status = {}, failed to update order status for id = {}", status, orderId);
			throw new BadRequestException("Invalid status = " + status);
		}

		Optional<Order> orderOpt = orderRepository.findById(orderId);
		if (orderOpt.isPresent()) {
			Order order = orderOpt.get();
			order.setOrderStatus(optOrderStatus.get().getString());
			orderRepository.save(order);
			log.info("Successfully updated order status to = {} for order id = {}", status.toUpperCase(), orderId);
		}
	}

	private Order generateOrder(OrderDto orderDto) {
		Order order = orderDetailsMapper.toOrderEntity(orderDto);
		order.setOrderId(UUID.randomUUID().toString());
		order.setCreatedAt(LocalDateTime.now());

		order.setOrderStatus(OrderStatus.PROCESSING.name());

		Payment payment = buildAndSavePayment(orderDto.getAmount(), orderDto.getPaymentMode());
		order.setPayment(payment);

		Address billingAddress = buildAndLoadAddress(orderDto.getBillingAddress());
		Address shippingAddress = buildAndLoadAddress(orderDto.getShippingAddress());
		order.setBillingAddress(billingAddress);
		order.setShippingAddress(shippingAddress);
		return order;
	}

	private List<OrderItem> buildOrderItems(List<OrderItemDto> orderItemsDtoList, String orderId) {
		List<OrderItem> orderItemList = orderItemsDtoList
				.stream()
				.map(orderItemDto -> new OrderItem(new OrderItemPk(orderItemDto.getProductId(), orderId), null, null,
						orderItemDto.getQuantity()))
				.toList();
		log.info("Saving order item list for order id = {}", orderId);
		return (List<OrderItem>) orderItemRepository.saveAll(orderItemList);
	}

	private Payment buildAndSavePayment(BigDecimal amount, String paymentMode) {
		Payment payment = new Payment(
				UUID.randomUUID().toString(),
				amount,
				paymentMode,
				UUID.randomUUID().toString(),
				PaymentStatus.PROCESSING.name(),
				LocalDateTime.now(),
				null);
		log.info("Saving payment details for payment id = {}", payment.getPaymentId());
		return paymentRepository.save(payment);
	}

	private Address buildAndLoadAddress(AddressDto addressDto) {
		Address addressEntity = orderDetailsMapper.toAddressEntity(addressDto);
		addressEntity.setAddressId(UUID.randomUUID().toString());
		addressEntity.setCreatedAt(LocalDateTime.now());
		log.info("Saving billing/shipping address for address id = {}", addressEntity.getAddressId());
		return addressRepository.save(addressEntity);
	}
}
