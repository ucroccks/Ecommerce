package com.order.ecommerce.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ecommerce_product")
public class Product implements Serializable {

	private static final long serialVersionUID = 10l;

	@Id
	@Column(name = "product_id", nullable = false, unique = true)
	private String productId;

	@Column(name = "sku", nullable = false)
	private String sku;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "createdAt", nullable = false)
	private LocalDateTime createdAt;

	@OneToMany(targetEntity = OrderItem.class, fetch = FetchType.LAZY, mappedBy = "product")
	private List<OrderItem> orderItems;
}
