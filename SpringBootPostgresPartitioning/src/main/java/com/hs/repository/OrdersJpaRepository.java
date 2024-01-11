package com.hs.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hs.model.Order;
import com.hs.model.Order.ArchiveStatus;

@Repository
public interface OrdersJpaRepository extends JpaRepository<Order, String> {

	Optional<Order> findByOrderIdAndArchiveStatus(String orderId, ArchiveStatus archiveStatus);

}
