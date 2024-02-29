package io.biker.management.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.order.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {

}
