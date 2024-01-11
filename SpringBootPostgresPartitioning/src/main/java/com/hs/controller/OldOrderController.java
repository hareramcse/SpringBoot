package com.hs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.model.UnPartitionedOrder;
import com.hs.repository.UnPartitionedOrdersJpaRepository;

@RestController
@RequestMapping("/v1/orders")
public class OldOrderController {

	@Autowired
	private UnPartitionedOrdersJpaRepository unPartitionedOrdersJpaRepository;

	@GetMapping("/{orderId}")
	public ResponseEntity<UnPartitionedOrder> getOrder(@PathVariable("orderId") String orderId) {
		return unPartitionedOrdersJpaRepository.findById(orderId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

}
