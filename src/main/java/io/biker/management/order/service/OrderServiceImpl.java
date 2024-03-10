package io.biker.management.order.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.biker.management.biker.entity.Biker;
import io.biker.management.customer.entity.Customer;
import io.biker.management.enums.OrderStatus;
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
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepo orderRepo;

    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Order createOrder(Customer customer, Product product, Address deliveryAddress) {
        log.info("Running createOrder(" + customer.toString() + ", " + product.toString() + ", "
                + deliveryAddress.toString() + ") in OrderServiceImpl...");
        if (product.getQuantity() < 1) {
            log.error("User attempting to order a product that is out of stock!");
            throw new OrderException(OrderExceptionMessages.OUT_OF_STOCK);
        }
        log.info("Creating order details...");
        OrderDetails orderDetails = new OrderDetails(product,
                Tax.VAT, product.getPrice() * (1 + Tax.VAT), deliveryAddress, null);
        log.info("Creating order...");
        Order order = new Order(0, customer, product.getStore(), null, OrderStatus.AWAITING_APPROVAL,
                null, orderDetails);

        log.info("Saving order...");
        return orderRepo.save(order);
    }

    @Override
    public Order getOrder(Customer customer, int orderId) {
        log.info("Running getOrder(" + customer.toString() + ", " + orderId + ") in ProductServiceImpl...");
        Optional<Order> opOrder = orderRepo.findByOrderIdAndCustomer(orderId, customer);

        return getOrderFromOptional(opOrder);
    }

    @Override
    public Order getOrder_BackOffice(int orderId) {
        log.info("Running getOrder_BackOffice(" + orderId + ") in ProductServiceImpl...");
        Optional<Order> opOrder = orderRepo.findById(orderId);

        return getOrderFromOptional(opOrder);
    }

    @Override
    public List<Order> getAvailableOrders() {
        log.info("Running getAvailableOrders() in ProductServiceImpl...");
        return orderRepo.findByStatus(OrderStatus.AWAITING_APPROVAL);
    }

    @Override
    public List<Order> getOrdersByStore(Store store) {
        log.info("Running getOrdersByStore(" + store.toString() + ") in ProductServiceImpl...");
        return orderRepo.findByStore(store);
    }

    @Override
    public List<Order> getOrdersByBiker(Biker biker) {
        log.info("Running getOrdersByBiker(" + biker.toString() + ") in ProductServiceImpl...");
        return orderRepo.findByBiker(biker);
    }

    @Override
    public void rateOrder(Customer customer, int orderId, FeedBack feedBack) {
        log.info("Running rateOrder(" + customer.toString() + ", " + orderId + ", "
                + feedBack.toString() + ") in OrderServiceImpl...");
        Order order = getOrder(customer, orderId);
        OrderDetails orderDetails = order.getOrderDetails();
        orderDetails.setFeedBack(feedBack);

        order.setOrderDetails(orderDetails);

        log.info("Saving updated order...");
        orderRepo.save(order);
    }

    @Override
    public void updateOrderStatus_Biker(Biker biker, int orderId, OrderStatus status) {
        log.info("Running updateOrderStatus_Biker(" + biker.toString() + ", " + orderId + ", "
                + status + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findByOrderIdAndBiker(orderId, biker);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);

        order.setStatus(status);
        log.info("Saving updated order...");
        orderRepo.save(order);
    }

    @Override
    public void updateOrderStatus_Store(Store store, int orderId, OrderStatus status) {
        log.info("Running updateOrderStatus_Store(" + store.toString() + ", " + orderId + ", "
                + status + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findByOrderIdAndStore(orderId, store);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);

        order.setStatus(status);
        log.info("Saving updated order...");
        orderRepo.save(order);
    }

    @Override
    public void updateOrderStatus_BackOffice(int orderId, OrderStatus status) {
        log.info("Running updateOrderStatus_BackOffice(" + orderId + ", " + status + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findById(orderId);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);

        order.setStatus(status);
        log.info("Saving updated order...");
        orderRepo.save(order);
    }

    @Override
    public Order updateOrderEstimatedTimeOfArrival_Biker(Biker biker, int orderId, Date estimatedTimeOfArrival) {
        log.info("Running updateOrderEstimatedTimeOfArrival_Biker(" + biker.toString() + ", " + orderId + ", "
                + estimatedTimeOfArrival.toString() + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findByOrderIdAndBiker(orderId, biker);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);
        order.setEstimatedTimeOfArrival(estimatedTimeOfArrival);

        log.info("Saving updated order...");
        return orderRepo.save(order);
    }

    @Override
    public Order updateOrderEstimatedTimeOfArrival_Store(Store store, int orderId, Date estimatedTimeOfArrival) {
        log.info("Running updateOrderEstimatedTimeOfArrival_Store(" + store.toString() + ", " + orderId + ", "
                + estimatedTimeOfArrival.toString() + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findByOrderIdAndStore(orderId, store);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);
        order.setEstimatedTimeOfArrival(estimatedTimeOfArrival);

        log.info("Saving updated order...");
        return orderRepo.save(order);
    }

    @Override
    public Order updateOrderEstimatedTimeOfArrival_BackOffice(int orderId, Date estimatedTimeOfArrival) {
        log.info("Running updateOrderEstimatedTimeOfArrival_BackOffice(" + orderId + ", " + estimatedTimeOfArrival.toString()
                + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findById(orderId);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);
        order.setEstimatedTimeOfArrival(estimatedTimeOfArrival);

        log.info("Saving updated order...");
        return orderRepo.save(order);
    }

    @Override
    public void assignDelivery(Biker biker, int orderId) {
        log.info("Running assignDelivery(" + biker.toString() + ", " + orderId + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findById(orderId);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);

        log.info("Assignin biker to order...");
        order.setBiker(biker);
        log.info("Saving updated order...");
        orderRepo.save(order);
    }

    @Override
    public void deleteOrder(int orderId) {
        log.info("Running deleteOrder(" + orderId + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findById(orderId);
        log.info("Attempting to find order...");
        getOrderFromOptional(opOrder);

        log.info("Deleting order...");
        orderRepo.deleteById(orderId);
    }

    // Helper functions
    private Order getOrderFromOptional(Optional<Order> opOrder) {
        if (opOrder.isPresent()) {
            return opOrder.get();
        } else {
            log.error("Order not found with provided paremeters!");
            throw new OrderException(OrderExceptionMessages.ORDER_NOT_FOUND);
        }
    }
}
