package io.biker.management.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.customer.entity.Customer;
import io.biker.management.order.entity.Order;

import java.util.List;
import java.util.Optional;
import io.biker.management.store.entity.Store;
import io.biker.management.biker.entity.Biker;

public interface OrderRepo extends JpaRepository<Order, Integer> {

    Optional<Order> findbyOrderIdAndCustomer(int orderId, Customer customer);

    Optional<Order> findbyOrderIdAndBiker(int orderId, Biker biker);

    Optional<Order> findbyOrderIdAndStore(int orderId, Store store);

    List<Order> findByStatus(String status);

    List<Order> findByStore(Store store);

    List<Order> findByBiker(Biker biker);
}
