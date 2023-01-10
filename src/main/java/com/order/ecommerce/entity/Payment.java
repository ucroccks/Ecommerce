package com.order.ecommerce.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ecommerce_payment")
public class Payment implements Serializable {

	private static final long serialVersionUID = 10l;

	@Id
	@Column(name = "payment_id", nullable = false, unique = true)
	private String paymentId;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@Column(name = "payment_mode", nullable = false)
	private String paymentMode;

	@Column(name = "confirmation_number", nullable = false)
	private String confirmationNumber;

	@Column(name = "payment_status", nullable = false)
	private String paymentStatus;

	@Column(name = "createdAt", nullable = false)
	private LocalDateTime createdAt;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
	private Order order;
}
