package com.hs.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "old_orders")
public class UnPartitionedOrder {

	@Id
	private String id;

	private String userId;

	private BigDecimal total;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

}
