package io.biker.management.order.service;

import java.sql.Date;
import java.util.List;

import io.biker.management.biker.entity.Biker;
import io.biker.management.customer.entity.Customer;
import io.biker.management.enums.OrderStatus;
import io.biker.management.order.entity.FeedBack;
import io.biker.management.order.entity.Order;
import io.biker.management.product.entity.Product;
import io.biker.management.store.entity.Store;
import io.biker.management.user.Address;

public interface OrderService {
    public Order createOrder(Customer customer, Product product, Address deliveryAddress);

    public Order getOrder(Customer customer, int orderId);

    public Order getOrder_BackOffice(int orderId);

    public List<Order> getAvailableOrders();

    public List<Order> getOrdersByStore(Store store);

    public List<Order> getOrdersByBiker(Biker biker);

    public void rateOrder(Customer customer, int orderId, FeedBack feedBack);

    public void updateOrderStatus_Biker(Biker biker, int orderId, OrderStatus status);

    public void updateOrderStatus_Store(Store store, int orderId, OrderStatus status);

    public void updateOrderStatus_BackOffice(int orderId, OrderStatus status);

    public Order updateOrderEta_Biker(Biker biker, int orderId, Date eta);

    public Order updateOrderEta_Store(Store store, int orderId, Date eta);

    public Order updateOrderEta_BackOffice(int orderId, Date eta);

    public void assignDelivery(Biker biker, int orderId);

    public void deleteOrder(int orderId);
}
