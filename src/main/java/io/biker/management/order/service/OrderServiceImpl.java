package io.biker.management.order.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.biker.management.biker.entity.Biker;
import io.biker.management.customer.entity.Customer;
import io.biker.management.order.constants.OrderStatus;
import io.biker.management.order.constants.Tax;
import io.biker.management.order.entity.FeedBack;
import io.biker.management.order.entity.Order;
import io.biker.management.order.entity.OrderDetails;
import io.biker.management.order.exception.OrderException;
import io.biker.management.order.exception.OrderExceptionMessages;
import io.biker.management.order.repo.OrderRepo;
import io.biker.management.product.entity.Product;
import io.biker.management.store.entity.Store;
import io.biker.management.user.Address;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepo orderRepo;

    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Order createOrder(Customer customer, Product product, Address deliveryAddress) {
        OrderDetails orderDetails = new OrderDetails(product,
                Tax.VAT, product.getPrice() * (1 + Tax.VAT), deliveryAddress, null);
        Order order = new Order(0, customer, product.getStore(), null, OrderStatus.AWAITING_APPROVAL,
                null, orderDetails);

        return orderRepo.save(order);
    }

    @Override
    public Order getOrder(Customer customer, int orderId) {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndCustomer(orderId, customer);

        return getOrderFromOptional(opOrder);
    }

    @Override
    public Order getOrder_BackOffice(int orderId) {
        Optional<Order> opOrder = orderRepo.findById(orderId);

        return getOrderFromOptional(opOrder);
    }

    @Override
    public List<Order> getAvailableOrders() {
        return orderRepo.findByStatus(OrderStatus.AWAITING_APPROVAL);
    }

    @Override
    public List<Order> getOrdersByStore(Store store) {
        return orderRepo.findByStore(store);
    }

    @Override
    public List<Order> getOrdersByBiker(Biker biker) {
        return orderRepo.findByBiker(biker);
    }

    @Override
    public void rateOrder(Customer customer, int orderId, FeedBack feedBack) {
        Order order = getOrder(customer, orderId);
        OrderDetails orderDetails = order.getOrderDetails();
        orderDetails.setFeedBack(feedBack);

        order.setOrderDetails(orderDetails);
        orderRepo.save(order);
    }

    @Override
    public void updateOrderStatus_Biker(Biker biker, int orderId, String status) {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndBiker(orderId, biker);
        Order order = getOrderFromOptional(opOrder);

        order.setStatus(status);
        orderRepo.save(order);
    }

    @Override
    public void updateOrderStatus_Store(Store store, int orderId, String status) {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndStore(orderId, store);
        Order order = getOrderFromOptional(opOrder);

        order.setStatus(status);
        orderRepo.save(order);
    }

    @Override
    public void updateOrderStatus_BackOffice(int orderId, String status) {
        Optional<Order> opOrder = orderRepo.findById(orderId);
        Order order = getOrderFromOptional(opOrder);

        order.setStatus(status);
        orderRepo.save(order);
    }

    @Override
    public Order updateOrderEta_Biker(Biker biker, int orderId, Date eta) {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndBiker(orderId, biker);
        Order order = getOrderFromOptional(opOrder);
        order.setEta(eta);

        return orderRepo.save(order);
    }

    @Override
    public Order updateOrderEta_Store(Store store, int orderId, Date eta) {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndStore(orderId, store);
        Order order = getOrderFromOptional(opOrder);
        order.setEta(eta);

        return orderRepo.save(order);
    }

    @Override
    public Order updateOrderEta_BackOffice(int orderId, Date eta) {
        Optional<Order> opOrder = orderRepo.findById(orderId);
        Order order = getOrderFromOptional(opOrder);
        order.setEta(eta);

        return orderRepo.save(order);
    }

    @Override
    public void assignDelivery(Biker biker, int orderId) {
        Optional<Order> opOrder = orderRepo.findById(orderId);
        Order order = getOrderFromOptional(opOrder);

        order.setBiker(biker);
        orderRepo.save(order);
    }

    @Override
    public void deleteOrder(int orderId) {
        Optional<Order> opOrder = orderRepo.findById(orderId);
        getOrderFromOptional(opOrder);

        orderRepo.deleteById(orderId);
    }

    // Helper functions
    private Order getOrderFromOptional(Optional<Order> opOrder) {
        if (opOrder.isPresent()) {
            return opOrder.get();
        } else {
            throw new OrderException(OrderExceptionMessages.ORDER_NOT_FOUND);
        }
    }
}
