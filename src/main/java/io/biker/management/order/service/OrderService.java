package io.biker.management.order.service;

import java.util.List;

import io.biker.management.customer.entity.Customer;
import io.biker.management.order.entity.Order;
import io.biker.management.product.entity.Product;
import io.biker.management.user.Address;

public interface OrderService {
    public Order createOrder(Customer customer, Product product, Address deliveryAddress);

    public Order getOrder(int customerId, int orderId);

    public Order getOrder_BackOffice(int orderId);

    public List<Order> getAvailableOrders();

    public List<Order> getOrdersByStore(int storeId);

    public List<Order> getOrdersByBiker(int bikerId);

    public void rateOrder(int customerId, int orderId);

    public void updateOrderStatus_Biker(int bikerId, int orderId, String status);

    public void updateOrderStatus_Store(int storeId, int orderId, String status);

    public void updateOrderStatus_BackOffice(int orderId, String status);

    public void assignDelivery(int bikerId, int orderId);

    public void deleteOrder(int orderId);
}
