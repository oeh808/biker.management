package io.biker.management.order.service;

import java.sql.Date;
import java.util.List;

import io.biker.management.enums.OrderStatus;
import io.biker.management.order.entity.FeedBack;
import io.biker.management.order.entity.Order;
import io.biker.management.user.Address;

public interface OrderService {
    public Order createOrder(int customerId, int productId, Address deliveryAddress);

    public Order getOrder(int customerId, int orderId);

    public Order getOrder(int orderId);

    public Order getOrder_BackOffice(int orderId);

    public List<Order> getAvailableOrders();

    public List<Order> getOrdersByStore(int storeId);

    public List<Order> getOrdersByBiker(int bikerId);

    public void rateOrder(int customerId, int orderId, FeedBack feedBack);

    public void updateOrderStatus_Biker(int bikerId, int orderId, OrderStatus status);

    public void updateOrderStatus_Store(int storeId, int orderId, OrderStatus status);

    public void updateOrderStatus_BackOffice(int orderId, OrderStatus status);

    public Order updateOrderEstimatedTimeOfArrival_Biker(int bikerId, int orderId, Date estimatedTimeOfArrival);

    public Order updateOrderEstimatedTimeOfArrival_Store(int storeId, int orderId, Date estimatedTimeOfArrival);

    public Order updateOrderEstimatedTimeOfArrival_BackOffice(int orderId, Date estimatedTimeOfArrival);

    public void assignDelivery(int bikerId, int orderId);

    public void deleteOrder(int orderId);
}
