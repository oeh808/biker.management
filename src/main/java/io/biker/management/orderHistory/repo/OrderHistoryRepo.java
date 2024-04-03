package io.biker.management.orderHistory.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.biker.management.orderHistory.entity.OrderHistory;
import java.util.List;
import java.util.Optional;

public interface OrderHistoryRepo extends MongoRepository<OrderHistory, Integer> {
    List<OrderHistory> findByOrder_OrderId(int orderId);

    Optional<OrderHistory> findByIdAndOrder_OrderId(int id, int orderId);

    void deleteByOrder_OrderId(int orderId);
}
