package io.biker.management.order.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.service.BikerService;
import io.biker.management.customer.entity.Customer;
import io.biker.management.customer.service.CustomerService;
import io.biker.management.enums.OrderStatus;
import io.biker.management.order.constants.Tax;
import io.biker.management.order.entity.FeedBack;
import io.biker.management.order.entity.Order;
import io.biker.management.order.entity.OrderDetails;
import io.biker.management.order.exception.OrderException;
import io.biker.management.order.exception.OrderExceptionMessages;
import io.biker.management.order.repo.OrderRepo;
import io.biker.management.orderHistory.entity.OrderHistory;
import io.biker.management.orderHistory.exception.OrderHistoryException;
import io.biker.management.orderHistory.exception.OrderHistoryExceptionMessages;
import io.biker.management.orderHistory.service.OrderHistoryService;
import io.biker.management.product.entity.Product;
import io.biker.management.product.service.ProductService;
import io.biker.management.store.entity.Store;
import io.biker.management.store.service.StoreService;
import io.biker.management.user.Address;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepo orderRepo;
    private CustomerService customerService;
    private ProductService productService;
    private StoreService storeService;
    private BikerService bikerService;
    private OrderHistoryService orderHistoryService;

    public OrderServiceImpl(OrderRepo orderRepo, CustomerService customerService, ProductService productService,
            StoreService storeService, BikerService bikerService, OrderHistoryService orderHistoryService) {
        this.orderRepo = orderRepo;
        this.customerService = customerService;
        this.productService = productService;
        this.storeService = storeService;
        this.bikerService = bikerService;
        this.orderHistoryService = orderHistoryService;
    }

    @Override
    public Order createOrder(int customerId, int productId, Address deliveryAddress) {
        log.info("Running createOrder(" + customerId + ", " + productId + ", "
                + deliveryAddress.toString() + ") in OrderServiceImpl...");
        Customer customer = customerService.getSingleCustomer(customerId);
        Product product = productService.getProduct(productId);

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
        order = orderRepo.save(order);

        orderHistoryService.createOrderHistory(order.getOrderId(), Date.valueOf(LocalDate.now()));

        return order;
    }

    @Override
    public Order getOrder(int customerId, int orderId) {
        log.info("Running getOrder(" + customerId + ", " + orderId + ") in OrderServiceImpl...");
        Customer customer = customerService.getSingleCustomer(customerId);
        Optional<Order> opOrder = orderRepo.findByOrderIdAndCustomer(orderId, customer);

        return getOrderFromOptional(opOrder);
    }

    @Override
    public Order getOrder(int orderId) {
        log.info("Running getOrder(" + orderId + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findById(orderId);

        return getOrderFromOptional(opOrder);
    }

    @Override
    public Order getOrder_BackOffice(int orderId) {
        log.info("Running getOrder_BackOffice(" + orderId + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findById(orderId);

        return getOrderFromOptional(opOrder);
    }

    @Override
    public List<Order> getAvailableOrders() {
        log.info("Running getAvailableOrders() in OrderServiceImpl...");
        return orderRepo.findByStatus(OrderStatus.AWAITING_APPROVAL);
    }

    @Override
    public List<Order> getOrdersByStore(int storeId) {
        log.info("Running getOrdersByStore(" + storeId + ") in OrderServiceImpl...");
        Store store = storeService.getSingleStore(storeId);

        return orderRepo.findByStore(store);
    }

    @Override
    public List<Order> getOrdersByBiker(int bikerId) {
        log.info("Running getOrdersByBiker(" + bikerId + ") in OrderServiceImpl...");
        Biker biker = bikerService.getSingleBiker(bikerId);

        return orderRepo.findByBiker(biker);
    }

    @Override
    public void rateOrder(int customerId, int orderId, FeedBack feedBack) {
        log.info("Running rateOrder(" + customerId + ", " + orderId + ", "
                + feedBack.toString() + ") in OrderServiceImpl...");

        Order order = getOrder(customerId, orderId);
        OrderDetails orderDetails = order.getOrderDetails();
        orderDetails.setFeedBack(feedBack);

        order.setOrderDetails(orderDetails);

        log.info("Saving updated order...");
        orderRepo.save(order);
    }

    @Override
    public void updateOrderStatus_Biker(int bikerId, int orderId, OrderStatus status) {
        log.info("Running updateOrderStatus_Biker(" + bikerId + ", " + orderId + ", "
                + status + ") in OrderServiceImpl...");
        Biker biker = bikerService.getSingleBiker(bikerId);

        Optional<Order> opOrder = orderRepo.findByOrderIdAndBiker(orderId, biker);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);

        order.setStatus(status);
        log.info("Saving updated order...");
        orderRepo.save(order);

        orderHistoryService.createOrderHistory(orderId, getOrderCreationDate(orderId));
    }

    @Override
    public void updateOrderStatus_Store(int storeId, int orderId, OrderStatus status) {
        log.info("Running updateOrderStatus_Store(" + storeId + ", " + orderId + ", "
                + status + ") in OrderServiceImpl...");
        Store store = storeService.getSingleStore(storeId);

        Optional<Order> opOrder = orderRepo.findByOrderIdAndStore(orderId, store);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);

        order.setStatus(status);
        log.info("Saving updated order...");
        orderRepo.save(order);

        orderHistoryService.createOrderHistory(orderId, getOrderCreationDate(orderId));
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

        orderHistoryService.createOrderHistory(orderId, getOrderCreationDate(orderId));
    }

    @Override
    public void updateOrderEstimatedTimeOfArrival_Biker(int bikerId, int orderId, Date estimatedTimeOfArrival) {
        log.info("Running updateOrderEstimatedTimeOfArrival_Biker(" + bikerId + ", " + orderId + ", "
                + estimatedTimeOfArrival.toString() + ") in OrderServiceImpl...");
        Biker biker = bikerService.getSingleBiker(bikerId);

        Optional<Order> opOrder = orderRepo.findByOrderIdAndBiker(orderId, biker);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);
        order.setEstimatedTimeOfArrival(estimatedTimeOfArrival);

        log.info("Saving updated order...");
        orderRepo.save(order);

        orderHistoryService.createOrderHistory(orderId, getOrderCreationDate(orderId));
    }

    @Override
    public void updateOrderEstimatedTimeOfArrival_Store(int storeId, int orderId, Date estimatedTimeOfArrival) {
        log.info("Running updateOrderEstimatedTimeOfArrival_Store(" + storeId + ", " + orderId + ", "
                + estimatedTimeOfArrival.toString() + ") in OrderServiceImpl...");
        Store store = storeService.getSingleStore(storeId);

        Optional<Order> opOrder = orderRepo.findByOrderIdAndStore(orderId, store);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);
        order.setEstimatedTimeOfArrival(estimatedTimeOfArrival);

        log.info("Saving updated order...");
        orderRepo.save(order);

        orderHistoryService.createOrderHistory(orderId, getOrderCreationDate(orderId));
    }

    @Override
    public void updateOrderEstimatedTimeOfArrival_BackOffice(int orderId, Date estimatedTimeOfArrival) {
        log.info("Running updateOrderEstimatedTimeOfArrival_BackOffice(" + orderId + ", "
                + estimatedTimeOfArrival.toString()
                + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findById(orderId);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);
        order.setEstimatedTimeOfArrival(estimatedTimeOfArrival);

        log.info("Saving updated order...");
        orderRepo.save(order);

        orderHistoryService.createOrderHistory(orderId, getOrderCreationDate(orderId));
    }

    @Override
    public void assignDelivery(int bikerId, int orderId) {
        log.info("Running assignDelivery(" + bikerId + ", " + orderId + ") in OrderServiceImpl...");
        Biker biker = bikerService.getSingleBiker(bikerId);

        Optional<Order> opOrder = orderRepo.findById(orderId);
        log.info("Attempting to find order...");
        Order order = getOrderFromOptional(opOrder);

        log.info("Assignin biker to order...");
        order.setBiker(biker);
        log.info("Saving updated order...");
        orderRepo.save(order);

        orderHistoryService.createOrderHistory(orderId, getOrderCreationDate(orderId));
    }

    @Override
    public void deleteOrder(int orderId) {
        log.info("Running deleteOrder(" + orderId + ") in OrderServiceImpl...");
        Optional<Order> opOrder = orderRepo.findById(orderId);
        log.info("Attempting to find order...");
        getOrderFromOptional(opOrder);

        orderHistoryService.deleteOrderHistoriesByOrder(orderId);
        log.info("Deleting order...");
        orderRepo.deleteById(orderId);
    }

    @Override
    // Scheduled to run every day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteDeliveredOrders() {
        log.info("Running deleteDeliveredOrders() in OrderServiceImpl...");

        log.info("Retrieving delivered orders...");
        List<Order> deliveredOrders = orderRepo.findByStatus(OrderStatus.DELIVERED);

        log.info("Deleting orders...");
        for (Order order : deliveredOrders) {
            orderHistoryService.deleteOrderHistoriesByOrder(order.getOrderId());
            log.info("Deleting order with id = " + order.getOrderId() + "...");
            orderRepo.deleteById(order.getOrderId());
        }
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

    private Date getOrderCreationDate(int orderId) {
        List<OrderHistory> orderHistories = orderHistoryService.getOrderHistoriesByOrder(orderId);
        if (orderHistories.isEmpty()) {
            throw new OrderHistoryException(OrderHistoryExceptionMessages.ORDER_HISTORY_DOES_NOT_EXIST);
        } else {
            return orderHistories.get(0).getOrderCreationDate();
        }
    }
}
