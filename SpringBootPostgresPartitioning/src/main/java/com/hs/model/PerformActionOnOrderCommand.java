package com.hs.model;

import java.util.Objects;

import com.hs.model.Order.OrderAction;

import lombok.Getter;

@Getter
public class PerformActionOnOrderCommand {

	private String orderId;
	private OrderAction action;

	public PerformActionOnOrderCommand(String orderId, OrderAction action) {
		this.orderId = Objects.requireNonNull(orderId);
		this.action = Objects.requireNonNull(action);
	}

}
