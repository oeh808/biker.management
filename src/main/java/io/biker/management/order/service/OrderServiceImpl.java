package io.biker.management.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.biker.management.customer.entity.Customer;
import io.biker.management.order.constants.OrderStatus;
import io.biker.management.order.constants.Tax;
import io.biker.management.order.entity.Order;
import io.biker.management.order.entity.OrderDetails;
import io.biker.management.order.repo.OrderRepo;
import io.biker.management.product.entity.Product;
import io.biker.management.user.Address;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepo orderRepo;

    @Override
    public Order createOrder(Customer customer, Product product, Address deliveryAddress) {
        OrderDetails orderDetails = new OrderDetails(product.getName(), product.getPrice(),
                Tax.VAT, product.getPrice() * (1 + Tax.VAT), deliveryAddress, null);
        Order order = new Order(0, customer, product.getStore(), null, OrderStatus.AWAITING_APPROVAL,
                null, orderDetails);

        return orderRepo.save(order);
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
    public void updateOrderStatus_Biker(int customerId, int orderId, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderStatus'");
    }

    @Override
    public void updateOrderStatus_Store(int customerId, int orderId, String status) {
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
