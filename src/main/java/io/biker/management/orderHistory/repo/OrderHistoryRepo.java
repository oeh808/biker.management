package io.biker.management.orderHistory.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.orderHistory.entity.OrderHistory;

public interface OrderHistoryRepo extends JpaRepository<OrderHistory, Integer> {

}
