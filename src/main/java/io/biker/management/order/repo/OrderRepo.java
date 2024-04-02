package io.biker.management.order.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.biker.management.customer.entity.Customer;
import io.biker.management.enums.OrderStatus;
import io.biker.management.order.entity.Order;

import java.util.List;
import java.util.Optional;
import io.biker.management.store.entity.Store;
import io.biker.management.biker.entity.Biker;

public interface OrderRepo extends MongoRepository<Order, Integer> {

    Optional<Order> findByOrderIdAndCustomer(int orderId, Customer customer);

    Optional<Order> findByOrderIdAndBiker(int orderId, Biker biker);

    Optional<Order> findByOrderIdAndStore(int orderId, Store store);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByStore(Store store);

    List<Order> findByBiker(Biker biker);
}
