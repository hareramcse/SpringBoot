package com.hs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hs.model.UnPartitionedOrder;

@Repository
public interface UnPartitionedOrdersJpaRepository extends JpaRepository<UnPartitionedOrder, String> {

}
