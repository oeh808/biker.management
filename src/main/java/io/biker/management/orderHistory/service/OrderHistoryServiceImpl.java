package io.biker.management.orderHistory.service;

import java.util.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.biker.management.order.entity.Order;
import io.biker.management.order.exception.OrderException;
import io.biker.management.order.exception.OrderExceptionMessages;
import io.biker.management.order.repo.OrderRepo;
import io.biker.management.orderHistory.entity.OrderHistory;
import io.biker.management.orderHistory.exception.OrderHistoryException;
import io.biker.management.orderHistory.exception.OrderHistoryExceptionMessages;
import io.biker.management.orderHistory.repo.OrderHistoryRepo;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {
    private OrderHistoryRepo orderHistoryRepo;
    private OrderRepo orderRepo;

    public OrderHistoryServiceImpl(OrderHistoryRepo orderHistoryRepo, OrderRepo orderRepo) {
        this.orderHistoryRepo = orderHistoryRepo;
        this.orderRepo = orderRepo;
    }

    @Override
    public OrderHistory createOrderHistory(int orderId, Date orderCreationDate) {
        log.info("Running createOrderHistory(" + orderId + ", "
                + orderCreationDate.toString() + ") in OrderHistoryServiceImpl...");

        Order order = getOrder(orderId);
        OrderHistory orderHistory = new OrderHistory(0, orderCreationDate, order.getStatus(),
                order.getEstimatedTimeOfArrival(),
                order.getBiker(), Instant.now(), order);

        log.info("Saving Order History...");
        return orderHistoryRepo.save(orderHistory);
    }

    @Override
    public List<OrderHistory> getAllOrderHistories() {
        log.info("Running getAllOrderHistories() in OrderHistoryServiceImpl...");
        return orderHistoryRepo.findAll();
    }

    @Override
    public List<OrderHistory> getOrderHistoriesByOrder(int orderId) {
        log.info("Running getOrderHistoriesByOrder(" + orderId + ") in OrderHistoryServiceImpl...");

        Order order = getOrder(orderId);

        log.info(orderHistoryRepo.findByOrder_OrderId(order.getOrderId()).size());
        return orderHistoryRepo.findByOrder_OrderId(order.getOrderId());
    }

    @Override
    public OrderHistory getSingleOrderHistory(int orderId, int id) {
        log.info("Running getSingleOrderHistory(" + orderId + ", " + id + ") in OrderHistoryServiceImpl...");

        Order order = getOrder(orderId);

        log.info("Retrieving order history...");
        Optional<OrderHistory> opOrderHistory = orderHistoryRepo.findByIdAndOrder_OrderId(id, order.getOrderId());
        if (opOrderHistory.isEmpty()) {
            log.error("Order History not found with provided parameters!");
            throw new OrderHistoryException(OrderHistoryExceptionMessages.ORDER_HISTORY_DOES_NOT_EXIST(orderId));
        } else {
            return opOrderHistory.get();
        }
    }

    @Override
    public void deleteOrderHistory(int orderId, int id) {
        log.info("Running deleteOrderHistory(" + orderId + ", " + id + ") in OrderHistoryServiceImpl...");

        getSingleOrderHistory(orderId, id);

        log.info("Deleting record of order history...");
        orderHistoryRepo.deleteById(id);
    }

    @Override
    public void deleteOrderHistoriesByOrder(int orderId) {
        log.info("Running deleteOrderHistoriesByOrder(" + orderId + ") in OrderHistoryServiceImpl...");

        getOrder(orderId);

        log.info("Deleting all records of order history for order...");
        orderHistoryRepo.deleteByOrder_OrderId(orderId);
    }

    // Helper functions
    private Order getOrder(int orderId) {
        log.info("Retrieving order with id: " + orderId + "...");
        Optional<Order> opOrder = orderRepo.findById(orderId);
        if (opOrder.isPresent()) {
            return opOrder.get();
        } else {
            log.error("Order with id: " + orderId + " not found!");
            throw new OrderException(OrderExceptionMessages.ORDER_NOT_FOUND);
        }
    }
}
