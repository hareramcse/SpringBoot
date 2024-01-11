package com.hs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hs.model.Order;
import com.hs.model.PerformActionOnOrderCommand;
import com.hs.model.Order.ArchiveStatus;
import com.hs.repository.OrdersJpaRepository;

@Service
public class OrderApplicationService {

	@Autowired
	private OrdersJpaRepository ordersJpaRepository;

	@Transactional
	public Order performActionOnOrder(PerformActionOnOrderCommand performActionOnOrderCommand) {
		return ordersJpaRepository
				.findByOrderIdAndArchiveStatus(performActionOnOrderCommand.getOrderId(), ArchiveStatus.ACTIVE)
				.map(order -> {
					order.performAction(performActionOnOrderCommand.getAction());
					return order;
				}).map(order -> ordersJpaRepository.save(order)).orElseThrow();
	}

}
