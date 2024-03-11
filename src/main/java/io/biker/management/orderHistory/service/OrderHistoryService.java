package io.biker.management.orderHistory.service;

import java.sql.Date;
import java.util.List;

import io.biker.management.orderHistory.entity.OrderHistory;

public interface OrderHistoryService {
    public OrderHistory createOrderHistory(int orderId, Date orderCreationDate);

    public List<OrderHistory> getAllOrderHistories();

    public List<OrderHistory> getOrderHistoriesByOrder(int orderId);

    public OrderHistory getSingleOrderHistory(int orderId, int id);

    public void deleteOrderHistory(int orderId, int id);

    public void deleteOrderHistoriesByOrder(int orderId);
}
