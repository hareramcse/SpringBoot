package com.hs.setup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hs.model.Order;
import com.hs.model.OrderStatus;
import com.hs.model.UnPartitionedOrder;
import com.hs.model.Order.ArchiveStatus;
import com.hs.repository.OrdersJpaRepository;
import com.hs.repository.UnPartitionedOrdersJpaRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SetupMockOrders {

	@Autowired
	private OrdersJpaRepository ordersJpaRepository;

	@Autowired
	private UnPartitionedOrdersJpaRepository unPartitionedOrdersJpaRepository;

	@PostConstruct
	@Transactional
	public void setUp() {
		int total = 10_000;
		int batchSize = 100;
		int batch = 1;

		List<Order> orders = new ArrayList<>();
		List<UnPartitionedOrder> unPartitionedOrders = new ArrayList<>();

		for (int i = 1; i <= total; i++) {

			Random random = new Random(i);
			String orderId = UUID.randomUUID().toString();
			String userId = UUID.randomUUID().toString();

			double randomDouble = random.nextDouble();
			OrderStatus status = OrderStatus.CREATED;
			ArchiveStatus archiveStatus = ArchiveStatus.ACTIVE;
			// You'll have much more archived orders than active orders
			if (randomDouble < 0.9) {
				status = OrderStatus.DELIVERED;
				archiveStatus = ArchiveStatus.ARCHIVED;
			}

			Double orderTotal = random.nextDouble() * 1000;

			Order order = new Order();
			order.setOrderId(orderId);
			order.setUserId(userId);
			order.setStatus(status);
			order.setArchiveStatus(archiveStatus);
			order.setTotal(BigDecimal.valueOf(orderTotal));

			orders.add(order);

			UnPartitionedOrder unPartitionedOrder = new UnPartitionedOrder();
			unPartitionedOrder.setId(orderId);
			unPartitionedOrder.setUserId(userId);
			unPartitionedOrder.setStatus(status);
			unPartitionedOrder.setTotal(BigDecimal.valueOf(orderTotal));

			unPartitionedOrders.add(unPartitionedOrder);

			if (i % batchSize == 0) {
				log.info("Processing orders batch " + batch + ". Progress = "
						+ ((batch * batchSize) / (double) total) * 100.0d + " %");
				ordersJpaRepository.saveAll(orders);
				unPartitionedOrdersJpaRepository.saveAll(unPartitionedOrders);

				orders.clear();
				unPartitionedOrders.clear();

				++batch;
			}
		}

	}

}
