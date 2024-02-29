package io.biker.management.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.biker.management.order.entity.Order;
import io.biker.management.user.Address;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(int customerId, int productId, Address deliveryAddress) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }

    @Override
    public Order getOrder(int customerId, int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrder'");
    }

    @Override
    public Order getOrder_BackOffice(int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrder_BackOffice'");
    }

    @Override
    public List<Order> getAvailableOrders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAvailableOrders'");
    }

    @Override
    public List<Order> getOrdersByStore(int storeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrdersByStore'");
    }

    @Override
    public List<Order> getOrdersByBiker(int bikerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrdersByBiker'");
    }

    @Override
    public void rateOrder(int customerId, int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rateOrder'");
    }

    @Override
    public void updateOrderStatus(int customerId, int orderId, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderStatus'");
    }

    @Override
    public void updateOrderStatus_BackOffice(int orderId, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderStatus_BackOffice'");
    }

    @Override
    public void assignDelivery(int bikerId, int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assignDelivery'");
    }

    @Override
    public void deleteOrder(int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOrder'");
    }

}
