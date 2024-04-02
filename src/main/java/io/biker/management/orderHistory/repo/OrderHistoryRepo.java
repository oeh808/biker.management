package io.biker.management.orderHistory.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.biker.management.orderHistory.entity.OrderHistory;
import java.util.List;
import java.util.Optional;

import io.biker.management.order.entity.Order;

public interface OrderHistoryRepo extends MongoRepository<OrderHistory, Integer> {
    List<OrderHistory> findByOrder(Order order);

    Optional<OrderHistory> findByIdAndOrder(int id, Order order);

    void deleteByOrder(Order order);
}
